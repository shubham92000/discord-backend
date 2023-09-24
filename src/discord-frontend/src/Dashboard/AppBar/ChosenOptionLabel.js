import React from 'react';
import { connect } from 'react-redux';
import { Typography } from '@mui/material';

const ChosenOptionLabel = ({ chat }) => {
	const name = chat.chosenChatDetails
		? chat.chosenChatDetails.name
			? chat.chosenChatDetails.name
			: ''
		: '';
	return (
		<Typography
			sx={{
				fontSize: '16px',
				color: 'white',
				fontWeight: 'bold',
			}}
		>
			{`${name ? `chosen conversations: ${name}` : ''}`}
		</Typography>
	);
};

const mapStateToProps = ({ chat }) => {
	return {
		chat,
	};
};

export default connect(mapStateToProps)(ChosenOptionLabel);
