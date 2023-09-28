import React from 'react';
import { styled } from '@mui/system';
import FriendsListItem from './FriendsListItem';
import { connect } from 'react-redux';

const MainContainer = styled('div')({
	flexGrow: 1,
	width: '100%',
});

const checkOnlineUsers = (conversationList = [], onlineUsers = []) => {
	conversationList.forEach((f) => {
		f.isOnline = false;
	});

	console.log('return friends', conversationList.length);
	return conversationList;
};

const FriendsList = ({ conversationList, onlineUsers }) => {
	return (
		<MainContainer>
			{checkOnlineUsers(conversationList, onlineUsers).map((f) => (
				<FriendsListItem
					username={f.userDetail.username}
					id={f.conversationId}
					key={f.conversationId}
					isOnline={f.isOnline}
					chatType={f.type}
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
