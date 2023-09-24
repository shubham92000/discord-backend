import React from 'react';
import { connect } from 'react-redux';
import { styled } from '@mui/system';
import MainPageButton from './MainPageButton';
import CreateRoomButton from './CreateRoomButton';

const MainContainer = styled('div')({
	width: '72px',
	height: '100%',
	display: 'flex',
	flexDirection: 'column',
	alignItems: 'center',
	backgroundColor: '#202225',
});

const Sidebar = () => {
	return (
		<MainContainer>
			<MainPageButton />
			<CreateRoomButton />
		</MainContainer>
	);
};

const mapStateToProps = ({ room }) => {
	return {
		...room,
	};
};

export default connect(mapStateToProps)(Sidebar);
