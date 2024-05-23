import React from 'react';
import { Box } from '@mui/material';

const TotalNewMessages = ({ newMessages }) => {
	return (
		<Box
			style={{
				display: 'flex',
				width: '18px',
				alignItems: 'center',
				justifyContent: 'center',
				position: 'absolute',
				right: '50px',
				borderRadius: '40%',
				backgroundColor: '#7b6b6b',
			}}
		>
			{newMessages}
		</Box>
	);
};

export default TotalNewMessages;
