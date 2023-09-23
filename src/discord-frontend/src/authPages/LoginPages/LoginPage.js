import React, { useEffect, useState } from 'react';
import AuthBox from '../../shared/components/AuthBox';
import LoginPageFooter from './LoginPageFooter';
import LoginPageHeader from './LoginPageHeader';
import LoginPageInputs from './LoginPageInputs';
import { validateLoginForm } from '../../shared/utils/validators';
import { connect } from 'react-redux';
import { getAction } from '../../store/actions/authActions';
import { useNavigate } from 'react-router-dom';

const LoginPage = ({ login }) => {
	let navigate = useNavigate();
	const [mail, setMail] = useState('');
	const [password, setPassword] = useState('');
	const [isFormValid, setIsFormValid] = useState(false);

	useEffect(() => {
		setIsFormValid(validateLoginForm({ mail, password }));
	}, [mail, password, setIsFormValid]);

	const handleLogin = () => {
		login(
			{
				email: mail,
				password,
			},
			navigate
		);
	};

	return (
		<AuthBox>
			<LoginPageHeader />
			<LoginPageInputs
				mail={mail}
				setMail={setMail}
				password={password}
				setPassword={setPassword}
			/>
			<LoginPageFooter isFormValid={isFormValid} handleLogin={handleLogin} />
		</AuthBox>
	);
};

const mapActionsToProps = (dispatch) => {
	return {
		...getAction(dispatch),
	};
};

export default connect(null, mapActionsToProps)(LoginPage);
