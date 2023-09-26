import { friendsAction } from '../actions/friendsActions';

const initState = {
	conversationList: [],
	pendingFriendsInvitations: [],
	onlineUsers: [],
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
		default:
			return state;
	}
};

export default reducer;
