import axios from 'axios';
// import { logout } from './shared/utils/auth';

const apiClient = axios.create({
	baseURL: 'http://localhost:5002/api',
	timeout: 1000,
});

apiClient.interceptors.request.use(
	(config) => {
		const userDetails = localStorage.getItem('user');

		if (userDetails) {
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
		return await apiClient.post('/auth/login', data);
	} catch (err) {
		return {
			error: true,
			err,
		};
	}
};

export const register = async (data) => {
	try {
		return await apiClient.post('/auth/register', data);
	} catch (err) {
		return {
			error: true,
			err,
		};
	}
};

// secure routes
export const sendFriendInvitation = async (data) => {
	try {
		return await apiClient.post('/friend-invitation/invite', data);
	} catch (err) {
		checkResponseCode(err);
		return {
			error: true,
			err,
		};
	}
};

export const acceptFriendInvitation = async (data) => {
	try {
		return await apiClient.post('/friend-invitation/accept', data);
	} catch (err) {
		checkResponseCode(err);
		return {
			error: true,
			err,
		};
	}
};

export const rejectFriendInvitation = async (data) => {
	try {
		return await apiClient.post('/friend-invitation/reject', data);
	} catch (err) {
		checkResponseCode(err);
		return {
			error: true,
			err,
		};
	}
};

const checkResponseCode = (exception) => {
	const responseCode = exception ? exception.response.status : null;

	if (responseCode && responseCode === 401 && responseCode === 403) {
		// logout();
	}
};
