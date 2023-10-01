import { friendsAction } from '../actions/friendsActions';

const initState = {
	conversationList: [],
	pendingFriendsInvitations: [],
	onlineUsers: [],
	newMessages: [],
};

const reducer = (state = initState, action) => {
	switch (action.type) {
		case friendsAction.SET_PENDING_FRIENDS_INVITATION:
			return {
				...state,
				pendingFriendsInvitations: action.pendingFriendsInvitations,
			};
		case friendsAction.SET_CONVERSATION_LIST:
			return {
				...state,
				conversationList: action.conversationList,
			};
		case friendsAction.SET_ONLINE_USERS:
			return {
				...state,
				onlineUsers: action.onlineUsers,
			};
		case friendsAction.SET_OFFLINE_USER:
			const newOnlineUsers = [...state.onlineUsers].filter(
				(e) => e !== action.offlineUser
			);
			return {
				...state,
				onlineUsers: newOnlineUsers,
			};
		case friendsAction.SET_NEW_MESSAGE:
			const unreadMessages = state.newMessages;
			const idx = unreadMessages.findIndex(
				(entry) => entry.conversationId === action.conversationId
			);
			if (idx === -1) {
				unreadMessages.unshift({
					conversationId: action.conversationId,
					totalMessages: action.totalMessages,
				});
			} else {
				unreadMessages[idx].totalMessages =
					unreadMessages[idx].totalMessages + action.totalMessages;
			}
			return {
				...state,
				newMessages: unreadMessages,
			};
		default:
			return state;
	}
};

export default reducer;
