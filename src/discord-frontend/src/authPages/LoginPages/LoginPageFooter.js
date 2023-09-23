import React from 'react';
import { useNavigate } from 'react-router-dom';
import CustomPrimaryButton from '../../shared/components/CustomPrimaryButton';
import RedirectInfo from '../../shared/components/RedirectInfo';
import { Tooltip } from '@mui/material';

const getFormNotValidMessage = () => {
	return 'Enter correct e-mail address and password must be between 6 to 12 characters';
};

const getFormValidMessage = () => {
	return 'press to  log in!';
};

const LoginPageFooter = ({ handleLogin, isFormValid }) => {
	const navigate = useNavigate();
	const handlePushToRegisterPage = () => {
		navigate('/register', { replace: true });
	};

	return (
		<>
			<Tooltip
				title={!isFormValid ? getFormNotValidMessage() : getFormValidMessage()}
			>
				<div>
					<CustomPrimaryButton
						label="Login"
						additionalStyles={{
							marginTop: '30px',
						}}
						disabled={!isFormValid}
						onClick={handleLogin}
					/>
				</div>
			</Tooltip>
			<RedirectInfo
				text="Need an account ? "
				redirectText="Create an account"
				additionalStyles={{ marginTop: '5px' }}
				redirectHandler={handlePushToRegisterPage}
			/>
		</>
	);
};

export default LoginPageFooter;
