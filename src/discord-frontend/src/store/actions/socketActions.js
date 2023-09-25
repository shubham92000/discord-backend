import * as api from '../../api';

export const socketAction = {
	SET_SOCKET_ID: 'SOCKET.SET_SOCKET_ID',
};

export const getsocketActions = (dispatch) => {
	return {
		fetchSocketId: (data) => dispatch(fetchSocketId(data)),
	};
};

const fetchSocketId = (data) => {
	return async (dispatch) => {
		const response = await api.generateSocketId(data);
		if (response.error) {
		} else {
			dispatch({
				type: socketAction.SET_SOCKET_ID,
				socketId: response.data.data.socketId,
			});
		}
	};
};
