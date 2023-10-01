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
	offlineUsers: (socketId) => {
		return `/user/${socketId}/topic/offline-users`;
	},
	chatHistory: (socketId) => {
		return `/user/${socketId}/topic/chat-history`;
	},
	message: (socketId) => {
		return `/user/${socketId}/topic/message`;
	},
};

export const publishTopics = {
	message: () => {
		return `/app/message`;
	},
	chatHistory: () => {
		return `/app/chat-history`;
	},
};
