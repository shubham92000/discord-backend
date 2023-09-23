import axios from 'axios';
import { logout } from './shared/utils/auth';

const apiClient = axios.create({});

apiClient.interceptors.request.use(
	(config) => {
		const userDetails = localStorage.getItem('user');

		if (
			userDetails !== null &&
			userDetails !== undefined &&
			userDetails !== 'undefined'
		) {
			const token = userDetails ? JSON.parse(userDetails).token : null;
			if (token) {
				config.headers.Authorization = `Bearer ${token}`;
			}
		}

		return config;
	},
	(err) => {
		return Promise.reject(err);
	}
);

export const login = async (data) => {
	try {
		return await apiClient.post('/api/auth/login', data);
	} catch (err) {
		// pass msg
		console.log('err: ', err);
		return {
			error: true,
			err,
			msg: errorMsg(err),
		};
	}
};

export const register = async (data) => {
	try {
		return await apiClient.post('/api/auth/register', data);
	} catch (err) {
		return {
			error: true,
			err,
			msg: errorMsg(err),
		};
	}
};

// secure routes
export const sendFriendInvitation = async (data) => {
	try {
		return await apiClient.post('/api/friend-invitation/invite', data);
	} catch (err) {
		// checkResponseCode(err);
		return {
			error: true,
			err,
			msg: errorMsg(err),
		};
	}
};

export const acceptFriendInvitation = async (data) => {
	try {
		return await apiClient.post('/api/friend-invitation/accept', data);
	} catch (err) {
		// checkResponseCode(err);
		return {
			error: true,
			err,
			msg: errorMsg(err),
		};
	}
};

export const rejectFriendInvitation = async (data) => {
	try {
		return await apiClient.post('/api/friend-invitation/reject', data);
	} catch (err) {
		// checkResponseCode(err);
		return {
			error: true,
			err,
			msg: errorMsg(err),
		};
	}
};

const checkResponseCode = (exception) => {
	const responseCode = exception ? exception.response.status : null;

	if (responseCode && responseCode === 401 && responseCode === 403) {
		logout();
	}
};

const errorMsg = (err) => {
	let msg = '';
	// check if api failure
	if (err.response?.data?.status === false) {
		msg = err.response.data.error.message;
	}
	// check if server failure
	else {
		msg = 'Internal Server Error';
	}
	return msg;
};
