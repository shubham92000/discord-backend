import * as api from '../../api';
import { openAlertMessage } from './alertActions';

export const authActions = {
	SET_USER_DETAILS: 'AUTH.SET_USER_DETAILS',
};

export const getAction = (dispatch) => {
	return {
		login: (userDetails, navigate) => dispatch(login(userDetails, navigate)),
		register: (userDetails, navigate) =>
			dispatch(register(userDetails, navigate)),
		setUserDetails: (userDetails) => dispatch(setUserDetails(userDetails)),
	};
};

const setUserDetails = (userDetails) => {
	return {
		type: authActions.SET_USER_DETAILS,
		userDetails,
	};
};

const login = (userDetails, navigate) => {
	return async (dispatch) => {
		const response = await api.login(userDetails);
		if (response.error) {
			console.log('response: ', response);
			dispatch(openAlertMessage(response.msg));
		} else {
			// const { userDetails } = response.data;
			const userDetails = response.data.data;
			localStorage.setItem('user', JSON.stringify(userDetails));
			dispatch(setUserDetails(userDetails));
			navigate('/dashboard', { replace: true });
		}
	};
};

const register = (userDetails, navigate) => {
	return async (dispatch) => {
		const response = await api.register(userDetails);
		if (response.error) {
			console.log('response: ', response);
			dispatch(openAlertMessage(response.msg));
		} else {
			// const { userDetails } = response.data;
			const userDetails = response.data.data;
			localStorage.setItem('user', JSON.stringify(userDetails));
			dispatch(setUserDetails(userDetails));
			navigate('/dashboard', { replace: true });
		}
	};
};
