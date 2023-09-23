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

function App() {
	return (
		<>
			<Router>
				<Routes>
					<Route path="/login" element={<LoginPage />} />
					<Route path="/" element={<div>Login</div>} />
				</Routes>
			</Router>
			<AlertNotification />
		</>
	);
}

export default App;
