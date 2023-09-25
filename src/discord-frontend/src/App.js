import React from 'react';
import {
	BrowserRouter as Router,
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

function App() {
	return (
		<>
			<Router>
				<Routes>
					<Route path="/login" element={<LoginPage />} />
					<Route path="/register" element={<RegisterPage />} />
					<Route path="/dashboard" element={<Dashboard />} />
					<Route
						path="*"
						element={
							<div>
								Not Found
								<Link to="/dashboard">Dashboard</Link>
								<Link to="/login">Login</Link>
							</div>
						}
					/>
				</Routes>
			</Router>
			<AlertNotification />
		</>
	);
}

export default App;
