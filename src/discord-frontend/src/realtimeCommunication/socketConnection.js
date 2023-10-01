import {
	setPendingFriendsInvitations,
	setConversationList,
	setOnlineUsers,
	setOfflineUser,
} from '../store/actions/friendsActions';
import {
	updateChatHisoryIfActiveChat,
	updateMessage,
} from '../shared/utils/chat';
import store from '../store/store';
import { Client } from '@stomp/stompjs';
import { publishTopics, subscribeTopics } from './socketTopics';

let stompClient = null;

export const connectWithSocketServer = (userDetails, socketId) => {
	const jwtToken = userDetails.token;

	stompClient = new Client({
		brokerURL: `ws://localhost:8080/socket?Authorization=Bearer ${jwtToken}`,
		reconnectDelay: 0,
	});

	stompClient.onConnect = (frame) => {
		console.log('Connected: ' + frame);

		stompClient.subscribe(
			subscribeTopics.friendInvitations(socketId),
			(data) => {
				const pendingInvitations = JSON.parse(data.body);
				store.dispatch(setPendingFriendsInvitations(pendingInvitations));
			}
		);

		stompClient.subscribe(
			subscribeTopics.conversationList(socketId),
			(data) => {
				const conversationList = JSON.parse(data.body);
				store.dispatch(setConversationList(conversationList));
			}
		);

		stompClient.subscribe(subscribeTopics.onlineUsers(socketId), (data) => {
			const onlineUsers = JSON.parse(data.body);
			store.dispatch(setOnlineUsers(onlineUsers));
		});

		stompClient.subscribe(subscribeTopics.offlineUsers(socketId), (data) => {
			const offlineUsers = data.body;
			store.dispatch(setOfflineUser(offlineUsers));
		});

		stompClient.subscribe(subscribeTopics.message(socketId), (data) => {
			const messageBody = JSON.parse(data.body);
			updateMessage(messageBody);
		});

		stompClient.subscribe(subscribeTopics.chatHistory(socketId), (data) => {
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
	stompClient.publish({
		destination: publishTopics.message(),
		body: JSON.stringify(data),
	});
};

export const getDirectChatHistory = (data) => {
	stompClient.publish({
		destination: publishTopics.chatHistory(),
		body: JSON.stringify(data),
	});
};
