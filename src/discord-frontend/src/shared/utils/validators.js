export const validateLoginForm = ({ mail, password }) => {
	const isMailValid = validateMail(mail);
	const isPasswordValid = validatePassword(password);

	return isMailValid && isPasswordValid;
};

export const validateRegisterForm = ({ mail, password, username }) => {
	return (
		validateMail(mail) &&
		validatePassword(password) &&
		validateUsername(username)
	);
};

export const validateMail = (mail) => {
	const mailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	return mailPattern.test(mail);
};

const validatePassword = (password) => {
	return password.length >= 6 && password.length < 12;
};

const validateUsername = (username) => {
	return username.length > 2 && username.length < 13;
};
