import React, { useEffect } from 'react';
import { styled } from '@mui/system';
import FriendsListItem from './FriendsListItem';
import { connect } from 'react-redux';

const data = [
	{
		userDetail: { username: 'abc' },
		conversationId: '1234565432',
		isOnline: true,
		type: 'DIRECT',
		totalNewMessages: 10,
	},
	{
		userDetail: { username: 'def' },
		conversationId: '1234565432dfg',
		isOnline: true,
		type: 'DIRECT',
		totalNewMessages: 12,
	},
];

const MainContainer = styled('div')({
	flexGrow: 1,
	width: '100%',
});

const checkOnlineUsers = (
	conversationList = [],
	onlineUsers = [],
	newMessages = []
) => {
	conversationList.forEach((f) => {
		f.isOnline = onlineUsers.includes(f.userDetail.email);
	});

	conversationList.forEach((f) => {
		f.totalNewMessages = 0;
		const idx = newMessages.findIndex(
			(entry) => entry.conversationId === f.conversationId
		);
		if (idx !== -1) {
			f.totalNewMessages = newMessages[idx].totalMessages;
		}
	});

	return conversationList;
};

const FriendsList = ({ conversationList, onlineUsers, newMessages }) => {
	useEffect(() => {}, [conversationList, onlineUsers, newMessages]);

	return (
		<MainContainer>
			{checkOnlineUsers(conversationList, onlineUsers, newMessages).map((f) => (
				<FriendsListItem
					username={f.userDetail.username}
					id={f.conversationId}
					key={f.conversationId}
					isOnline={f.isOnline}
					chatType={f.type}
					totalNewMessages={f.totalNewMessages}
				/>
			))}
		</MainContainer>
	);
};

const mapStateToProps = ({ friends }) => {
	return {
		...friends,
	};
};

export default connect(mapStateToProps)(FriendsList);
