import { openAlertMessage } from './alertActions';
import * as api from '../../api';

export const friendsAction = {
	SET_FRIENDS: 'FRIENDS.SET_FRIENDS',
	SET_PENDING_FRIENDS_INVITATION: 'FRIENDS.SET_PENDING_FRIENDS_INVITATION',
	SET_ONLINE_USERS: 'FRIENDS.SET_ONLINE_USERS',
};

export const getActions = (dispatch) => {
	return {
		sendFriendInvitation: (data, closeDialogHandler) =>
			dispatch(sendFriendInvitation(data, closeDialogHandler)),
		acceptFriendInvitation: (data) => dispatch(acceptFriendInvitation(data)),
		rejectFriendInvitation: (data) => dispatch(rejectFriendInvitation(data)),
		setFriends: (data) => dispatch(setFriends(data)),
	};
};

const sendFriendInvitation = (data, closeDialogHandler) => {
	return async (dispatch) => {
		const response = await api.sendFriendInvitation(data);
		if (response.error) {
			dispatch(openAlertMessage(response.err.response.data));
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

export const setFriends = (friends) => {
	return {
		type: friendsAction.SET_FRIENDS,
		friends,
	};
};

export const setOnlineUsers = (onlineUsers) => {
	return {
		type: friendsAction.SET_ONLINE_USERS,
		onlineUsers,
	};
};
