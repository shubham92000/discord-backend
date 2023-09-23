import React from 'react';
import { useNavigate } from 'react-router-dom';
import CustomPrimaryButton from '../../shared/components/CustomPrimaryButton';
import RedirectInfo from '../../shared/components/RedirectInfo';
import { Tooltip } from '@mui/material';

const getFormNotValidMessage = () => {
	return 'Username should be between 3 and 12 characters and password should be between 6 and 12 characters and please enter a valid email';
};

const getFormValidMessage = () => {
	return 'press to  register';
};

const RegisterPageFooter = ({ handleRegister, isFormValid }) => {
	const navigate = useNavigate();
	const handlePushToLoginPage = () => {
		navigate('/login', { replace: true });
	};

	return (
		<>
			<Tooltip
				title={!isFormValid ? getFormNotValidMessage() : getFormValidMessage()}
			>
				<div>
					<CustomPrimaryButton
						label="Register"
						additionalStyles={{
							marginTop: '30px',
						}}
						disabled={!isFormValid}
						onClick={handleRegister}
					/>
				</div>
			</Tooltip>
			<RedirectInfo
				text=""
				redirectText="Already have an account ?"
				additionalStyles={{ marginTop: '5px' }}
				redirectHandler={handlePushToLoginPage}
			/>
		</>
	);
};

export default RegisterPageFooter;
