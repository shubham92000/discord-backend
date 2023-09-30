export const chatTypes = {
	DIRECT: 'DIRECT',
	GROUP: 'GEOUP',
};

export const chatActions = {
	SET_CHOSEN_CHAT_DETAILS: 'CHAT.SET_CHOSEN_CHAT_DETAILS',
	SET_MESSAGES: 'CHAT.SET_MESSAGES',
	SET_ADD_MESSAGE: 'CHAT.SET_ADD_MESSAGE',
	SET_CHAT_TYPE: 'CHAT.SET_CHAT_TYPE',
};

export const getActions = (dispatch) => {
	return {
		setChosenChatDetails: (details, chatType) =>
			dispatch(setChosenChatDetails(details, chatType)),
	};
};

export const setChosenChatDetails = (chatDetails, type) => {
	return {
		type: chatActions.SET_CHOSEN_CHAT_DETAILS,
		chatType: type,
		chatDetails,
	};
};

export const setMessage = (messages) => {
	return {
		type: chatActions.SET_MESSAGES,
		messages,
	};
};

export const setAddMessage = (message) => {
	return {
		type: chatActions.SET_ADD_MESSAGE,
		message,
	};
};
