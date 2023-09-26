import {
	setPendingFriendsInvitations,
	setFriends,
	setOnlineUsers,
} from '../store/actions/friendsActions';
// import { updateDirectChatHistoryIfActive } from '../shared/utils/chat';
import store from '../store/store';
import { Client } from '@stomp/stompjs';
import { subscribeComplete } from '../api';

let socket = null;
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

		// stompClient.subscribe(`/topic/greetings2`, (data) => {
		// 	console.log('greetings :', data);
		// 	console.log('greetings :', data.body);
		// });

		// stompClient.subscribe(`/user/${socketId}/topic/greetings`, (data) => {
		// 	console.log('greetings :', data);
		// 	console.log('greetings :', data.body);
		// });

		stompClient.subscribe(
			`/user/${socketId}/topic/conversation-ids`,
			(data) => {
				console.log('conversation-ids :', data.body);
			}
		);

		stompClient.subscribe(
			`/user/${socketId}/topic/friends-invitations`,
			(data) => {
				// msg body as json string
				console.log('friends-invitations :', data.body);
				const pendingInvitations = JSON.parse(data.body);
				store.dispatch(setPendingFriendsInvitations(pendingInvitations));
			}
		);

		stompClient.subscribe(`/user/${socketId}/topic/friends-list`, (data) => {
			console.log('friends-list :', data.body);
		});

		stompClient.subscribe(`/user/${socketId}/topic/online-users`, (data) => {
			console.log('online-users :', data.body);
		});

		stompClient.subscribe(
			`/user/${socketId}/topic/direct-chat-history`,
			(data) => {
				console.log('direct-chat-history :', data.body);
			}
		);
	};

	stompClient.onWebSocketError = (error) => {
		console.error('Error with websocket', error);
	};

	stompClient.onStompError = (frame) => {
		console.error('Broker reported error: ' + frame.headers['message']);
		console.error('Additional details: ' + frame.body);
	};

	stompClient.activate();

	subscribeComplete();

	// socket.on('connect', () => {
	// 	console.log('successfully connected with socket io server');
	// 	console.log(socket.id);
	// });

	// socket.on('friends-invitations', (data) => {
	// 	const { pendingInvitations } = data;
	// 	store.dispatch(setPendingFriendsInvitations(pendingInvitations));
	// });

	// socket.on('friends-list', (data) => {
	// 	const { friends } = data;
	// 	store.dispatch(setFriends(friends));
	// });

	// socket.on('online-users', (data) => {
	// 	const { onlineUsers } = data;
	// 	store.dispatch(setOnlineUsers(onlineUsers));
	// });

	// socket.on('direct-chat-history', (data) => {
	// 	updateDirectChatHistoryIfActive(data);
	// });
};

export const sendDirectMessage = (data) => {
	console.log('send direct-message: ', data);
	stompClient.publish({
		destination: '/app/direct-message',
		body: JSON.stringify(data),
	});
};

export const getDirectChatHistory = (data) => {
	console.log('send direct-chat-history: ', data);
	stompClient.publish({
		destination: '/app/direct-chat-history',
		body: JSON.stringify(data),
	});
	// socket.emit('direct-chat-history', data);
};
