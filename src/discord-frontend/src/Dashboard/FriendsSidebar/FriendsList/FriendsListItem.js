import React from 'react';
import { connect } from 'react-redux';
import Button from '@mui/material/Button';
import Avatar from '../../../shared/components/Avatar';
import { Typography } from '@mui/material';
import OnlineIndicator from './OnlineIndicator';
import { getActions } from '../../../store/actions/chatActions';
import { getActions as getFriendActions } from '../../../store/actions/friendsActions';

const FriendsListItem = ({
	id,
	username,
	isOnline,
	setChosenChatDetails,
	chatType,
	totalNewMessages,
	clearNewMessage,
}) => {
	const handleChooseActiveConversation = () => {
		clearNewMessage(id);
		setChosenChatDetails(
			{
				conversationId: id,
				name: username,
			},
			chatType
		);
	};

	return (
		<Button
			onClick={handleChooseActiveConversation}
			style={{
				width: '100%',
				height: '42px',
				marginTop: '10px',
				display: 'flex',
				alignItems: 'center',
				justifyContent: 'flex-start',
				textTransform: 'none',
				color: 'black',
				position: 'relative',
			}}
		>
			<Avatar username={username} />
			<Typography
				style={{
					marginLeft: '7px',
					fontWeight: 700,
					color: '#8e9297',
				}}
				variant="subtitle1"
				align="left"
			>
				{username}
			</Typography>
			{totalNewMessages}
			{isOnline && <OnlineIndicator />}
		</Button>
	);
};

const mapActionsToProps = (dispatch) => {
	return {
		...getActions(dispatch),
		...getFriendActions(dispatch),
	};
};

export default connect(null, mapActionsToProps)(FriendsListItem);
