import { openAlertMessage } from './alertActions';
import * as api from '../../api';

export const friendsAction = {
	SET_CONVERSATION_LIST: 'FRIENDS.SET_CONVERSATION_LIST',
	SET_PENDING_FRIENDS_INVITATION: 'FRIENDS.SET_PENDING_FRIENDS_INVITATION',
	SET_ONLINE_USERS: 'FRIENDS.SET_ONLINE_USERS',
	SET_OFFLINE_USER: 'FRIENDS.SET_OFFLINE_USER',
	SET_NEW_MESSAGE: 'FRIENDS.SET_NEW_MESSAGE',
	SET_CLEAR_MESSAGE_INDICATOR: 'FRIENDS.SET_CLEAR_MESSAGE_INDICATOR',
};

export const getActions = (dispatch) => {
	return {
		sendFriendInvitation: (data, closeDialogHandler) =>
			dispatch(sendFriendInvitation(data, closeDialogHandler)),
		acceptFriendInvitation: (data) => dispatch(acceptFriendInvitation(data)),
		rejectFriendInvitation: (data) => dispatch(rejectFriendInvitation(data)),
		setConversationList: (conversationList) =>
			dispatch(setConversationList(conversationList)),
		clearNewMessage: (conversationId) =>
			dispatch(clearNewMessage(conversationId)),
	};
};

const sendFriendInvitation = (data, closeDialogHandler) => {
	return async (dispatch) => {
		const response = await api.sendFriendInvitation(data);
		if (response.error) {
			dispatch(openAlertMessage(response.msg));
		} else {
			dispatch(openAlertMessage('Invitation has been sent!'));
			closeDialogHandler();
		}
	};
};

export const setPendingFriendsInvitations = (pendingFriendsInvitations) => {
	return {
		type: friendsAction.SET_PENDING_FRIENDS_INVITATION,
		pendingFriendsInvitations,
	};
};

const acceptFriendInvitation = (data) => {
	return async (dispatch) => {
		const response = await api.acceptFriendInvitation(data);
		if (response.error) {
			dispatch(openAlertMessage(response.err.response.data));
		} else {
			dispatch(openAlertMessage('Invitation accepted!'));
		}
	};
};

const rejectFriendInvitation = (data) => {
	return async (dispatch) => {
		const response = await api.rejectFriendInvitation(data);
		if (response.error) {
			dispatch(openAlertMessage(response.err.response.data));
		} else {
			dispatch(openAlertMessage('Invitation rejected!'));
		}
	};
};

export const setConversationList = (conversationList) => {
	return {
		type: friendsAction.SET_CONVERSATION_LIST,
		conversationList,
	};
};

export const setOnlineUsers = (onlineUsers) => {
	return {
		type: friendsAction.SET_ONLINE_USERS,
		onlineUsers,
	};
};

export const setOfflineUser = (offlineUser) => {
	return {
		type: friendsAction.SET_OFFLINE_USER,
		offlineUser,
	};
};

export const newMessage = (conversationId, totalMessages) => {
	return {
		type: friendsAction.SET_NEW_MESSAGE,
		conversationId,
		totalMessages,
	};
};

export const clearNewMessage = (conversationId) => {
	return {
		type: friendsAction.SET_CLEAR_MESSAGE_INDICATOR,
		conversationId,
	};
};
