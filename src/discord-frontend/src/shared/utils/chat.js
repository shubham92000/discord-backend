import store from '../../store/store';
import { setAddMessage, setMessage } from '../../store/actions/chatActions';
import { newMessage } from '../../store/actions/friendsActions';

export const updateChatHisoryIfActiveChat = (data) => {
	const { conversationId, participants, messages, type, groupId } = data;

	// find the conversationId of active chat
	// if match, then update store messages

	const activeConversationId = store.getState().chat.chosenChatDetails
		? store.getState().chat.chosenChatDetails.conversationId
		: null;

	if (activeConversationId === conversationId) {
		store.dispatch(setMessage(messages));
	}
};

export const updateMessage = (messageBody) => {
	const { conversationId, message } = messageBody;

	const activeConversationId = store.getState().chat.chosenChatDetails
		? store.getState().chat.chosenChatDetails.conversationId
		: null;

	if (activeConversationId === conversationId) {
		store.dispatch(setAddMessage(message));
	} else {
		// display number of new messages for the other chat
		store.dispatch(newMessage(conversationId, 1));
	}
};
