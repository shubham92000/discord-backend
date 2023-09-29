import store from '../../store/store';
import { setMessage } from '../../store/actions/chatActions';

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
