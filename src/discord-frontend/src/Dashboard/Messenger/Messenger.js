import React from 'react';
import { styled } from '@mui/system';
import { connect } from 'react-redux';
import WelcomeMessage from './WelcomeMessage';
import MessengerContent from './MessengerContent';

const MainContainer = styled('div')({
	flexGrow: 1,
	backgroundColor: '#35393F',
	marginTop: '48px',
	display: 'flex',
});

const Messenger = ({ chosenChatDetails }) => {
	return (
		<MainContainer>
			{!chosenChatDetails ? (
				<WelcomeMessage chosenChatDetails={chosenChatDetails} />
			) : (
				<MessengerContent chosenChatDetails={chosenChatDetails} />
			)}
		</MainContainer>
	);
};

const mapStateToProps = ({ chat }) => {
	return {
		...chat,
	};
};

export default connect(mapStateToProps)(Messenger);
