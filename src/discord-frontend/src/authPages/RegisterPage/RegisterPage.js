import { Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import AuthBox from '../../shared/components/AuthBox';
import RegisterPageFooter from './RegisterPageFooter';
import RegisterPageInputs from './RegisterPageInputs';
import { validateRegisterForm } from '../../shared/utils/validators';
import { connect } from 'react-redux';
import { getAction } from '../../store/actions/authActions';
import { useNavigate } from 'react-router-dom';

const RegisterPage = ({ register, userDetails }) => {
	let navigate = useNavigate();
	const [mail, setMail] = useState('');
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');

	const [isFormValid, setIsFormValid] = useState(false);

	useEffect(() => {
		if (userDetails) {
			navigate('/dashboard', { replace: true });
		}
	}, [userDetails]);

	const handleRegister = () => {
		register(
			{
				email: mail,
				username,
				password,
				userType: 'user',
			},
			navigate
		);
	};

	useEffect(() => {
		setIsFormValid(
			validateRegisterForm({
				mail,
				username,
				password,
			})
		);
	}, [mail, username, password, setIsFormValid]);

	return (
		<AuthBox>
			<Typography variant="h5" sx={{ color: 'white' }}>
				Create an Account
			</Typography>
			<RegisterPageInputs
				mail={mail}
				setMail={setMail}
				username={username}
				setUsername={setUsername}
				password={password}
				setPassword={setPassword}
			/>
			<RegisterPageFooter
				handleRegister={handleRegister}
				isFormValid={isFormValid}
			/>
		</AuthBox>
	);
};

const mapStateToProps = ({ auth }) => {
	return {
		...auth,
	};
};

const mapActionsToProps = (dispatch) => {
	return {
		...getAction(dispatch),
	};
};

export default connect(mapStateToProps, mapActionsToProps)(RegisterPage);
