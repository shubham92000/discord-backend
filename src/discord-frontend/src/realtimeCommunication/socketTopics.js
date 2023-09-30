export const subscribeTopics = {
	friendInvitations: (socketId) => {
		return `/user/${socketId}/topic/friends-invitations`;
	},
	conversationList: (socketId) => {
		return `/user/${socketId}/topic/conversation-list`;
	},
	onlineUsers: (socketId) => {
		return `/user/${socketId}/topic/online-users`;
	},
	chatHistory: (socketId) => {
		return `/user/${socketId}/topic/chat-history`;
	},
};

export const publishTopics = {
	message: () => {
		return `/app/message`;
	},
	chatHistory: () => {
		return `/app/chat-history;`;
	},
};
