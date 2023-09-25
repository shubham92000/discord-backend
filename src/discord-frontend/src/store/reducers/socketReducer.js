import { socketAction } from '../actions/socketActions';

const initState = {
	socketId: null,
};

const reducer = (state = initState, action) => {
	switch (action.type) {
		case socketAction.SET_SOCKET_ID:
			return {
				...state,
				socketId: action.socketId,
			};
		default:
			return state;
	}
};

export default reducer;
