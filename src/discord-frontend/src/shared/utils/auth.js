import { redirect } from 'react-router-dom';

export const logout = () => {
	localStorage.clear();
	// window.location.pathname = '/';
	return redirect('/');
};
