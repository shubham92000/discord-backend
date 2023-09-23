import React from 'react';
import {
	BrowserRouter as Router,
	Routes,
	Route,
	Navigate,
} from 'react-router-dom';
import './App.css';

function App() {
	return (
		<>
			<Router>
				<Routes>
					<Route path="/" element={<div>Login</div>} />
				</Routes>
			</Router>
		</>
	);
}

export default App;
