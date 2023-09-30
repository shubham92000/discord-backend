import {
	setPendingFriendsInvitations,
	setConversationList,
	setOnlineUsers,
} from '../store/actions/friendsActions';
import { updateChatHisoryIfActiveChat } from '../shared/utils/chat';
import store from '../store/store';
import { Client } from '@stomp/stompjs';
import { publishTopics, subscribeTopics } from './socketTopics';

let stompClient = null;

export const connectWithSocketServer = (userDetails, socketId) => {
	const jwtToken = userDetails.token;
	console.log('token: ', jwtToken);
	console.log('socketId: ', socketId);

	stompClient = new Client({
		brokerURL: `ws://localhost:8080/socket?Authorization=Bearer ${jwtToken}`,
	});

	stompClient.onConnect = (frame) => {
		console.log('Connected: ' + frame);

		stompClient.subscribe(
			subscribeTopics.friendInvitations(socketId),
			(data) => {
				console.log('friends-invitations :', data.body);
				const pendingInvitations = JSON.parse(data.body);
				store.dispatch(setPendingFriendsInvitations(pendingInvitations));
			}
		);

		stompClient.subscribe(
			subscribeTopics.conversationList(socketId),
			(data) => {
				console.log('conversation-list :', data.body);
				const conversationList = JSON.parse(data.body);
				store.dispatch(setConversationList(conversationList));
			}
		);

		stompClient.subscribe(subscribeTopics.onlineUsers(socketId), (data) => {
			console.log('online-users :', data.body);
		});

		stompClient.subscribe(subscribeTopics.chatHistory(socketId), (data) => {
			console.log('chat-history :', data.body);
			const conversationDetails = JSON.parse(data.body);
			updateChatHisoryIfActiveChat(conversationDetails);
		});
	};

	stompClient.onWebSocketError = (error) => {
		console.error('Error with websocket', error);
	};

	stompClient.onStompError = (frame) => {
		console.error('Broker reported error: ' + frame.headers['message']);
		console.error('Additional details: ' + frame.body);
	};

	stompClient.activate();
};

export const sendDirectMessage = (data) => {
	console.log('send message: ', data);
	stompClient.publish({
		destination: publishTopics.message(),
		body: JSON.stringify(data),
	});
};

export const getDirectChatHistory = (data) => {
	console.log('send chat-history: ', data);
	stompClient.publish({
		destination: publishTopics.chatHistory(),
		body: JSON.stringify(data),
	});
};
