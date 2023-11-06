import React, { useEffect } from 'react';
import {
	HashRouter as Router,
	Routes,
	Route,
	Navigate,
} from 'react-router-dom';
import './App.css';
import LoginPage from './authPages/LoginPages/LoginPage';
import AlertNotification from './shared/components/AlertNotification';
import RegisterPage from './authPages/RegisterPage/RegisterPage';
import Dashboard from './Dashboard/Dashboard';
import { Link } from 'react-router-dom';
import { logout } from './shared/utils/auth';
import { connect } from 'react-redux';
import { getAction } from './store/actions/authActions';

function App({ setUserDetails }) {
	useEffect(() => {
		const userDetailsInJson = localStorage.getItem('user');
		if (!userDetailsInJson) {
			logout();
		} else {
			const userData = JSON.parse(userDetailsInJson);
			setUserDetails(userData);
		}
	}, []);

	return (
		<>
			<Router>
				<Routes>
					<Route path="/login" element={<LoginPage />} />
					<Route path="/register" element={<RegisterPage />} />
					<Route path="/dashboard" element={<Dashboard />} />
					<Route path="*" element={<Navigate to="/login" replace={true} />} />
				</Routes>
			</Router>
			<AlertNotification />
		</>
	);
}

const mapActionsToProps = (dispatch) => {
	return {
		...getAction(dispatch),
	};
};

export default connect(null, mapActionsToProps)(App);
