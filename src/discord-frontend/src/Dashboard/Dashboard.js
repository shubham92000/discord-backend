import React, { useEffect } from 'react';
import { styled } from '@mui/system';
import Sidebar from './Sidebar/Sidebar';
import FriendsSidebar from './FriendsSidebar/FriendsSidebar';
import Messenger from './Messenger/Messenger';
import AppBar from './AppBar/AppBar';
import { connect } from 'react-redux';
import { getAction } from '../store/actions/authActions';
import { getsocketActions } from '../store/actions/socketActions';
import { connectWithSocketServer } from '../realtimeCommunication/socketConnection';

const Wrapper = styled('div')({
	width: '100%',
	height: '100vh',
	display: 'flex',
});

const Dashboard = ({ fetchSocketId, socketId, userDetails }) => {
	useEffect(() => {
		if (userDetails !== null && userDetails.email !== null) {
			fetchSocketId({ email: userDetails.email });
		}
	}, []);

	useEffect(() => {
		if (socketId !== null) {
			connectWithSocketServer(userDetails, socketId);
		}
	}, [socketId]);

	return (
		<Wrapper>
			<Sidebar />
			<FriendsSidebar />
			<Messenger />
			<AppBar />
			{/* {isUserInRoom && <Room />} */}
		</Wrapper>
	);
};

const mapStateToProps = ({ socket, auth }) => {
	return {
		...socket,
		...auth,
	};
};

const mapActionsToProps = (dispatch) => {
	return {
		...getAction(dispatch),
		...getsocketActions(dispatch),
	};
};

export default connect(mapStateToProps, mapActionsToProps)(Dashboard);
