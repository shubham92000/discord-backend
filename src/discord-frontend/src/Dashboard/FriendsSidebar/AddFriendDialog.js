import {
	Dialog,
	DialogActions,
	DialogContent,
	DialogContentText,
	DialogTitle,
	Typography,
} from '@mui/material';
import React, { useEffect, useState } from 'react';
import { validateMail } from '../../shared/utils/validators';
import InputWithLabel from '../../shared/components/InputWithLabel';
import CustomPrimaryButton from '../../shared/components/CustomPrimaryButton';
import { connect } from 'react-redux';
import { getActions } from '../../store/actions/friendsActions';

const AddFriendDialog = ({
	isDialogOpen,
	closeDialogHandler,
	sendFriendInvitation = () => {},
}) => {
	const [mail, setMail] = useState('');
	const [isFormValid, setIsFormValid] = useState(false);

	const handleSendInvitation = () => {
		sendFriendInvitation(
			{
				targetMailAddress: mail,
			},
			handleCloseDialog
		);
	};

	const handleCloseDialog = () => {
		closeDialogHandler();
		setMail('');
	};

	useEffect(() => {
		setIsFormValid(validateMail(mail));
	}, [mail, setIsFormValid]);

	return (
		<div>
			<Dialog open={isDialogOpen} onClose={handleCloseDialog}>
				<DialogTitle>
					<Typography>Invite a Friend</Typography>
				</DialogTitle>
				<DialogContent>
					<DialogContentText>
						Enter email address of friend which you would like to invite
					</DialogContentText>
					<InputWithLabel
						label="Mail"
						type="text"
						value={mail}
						setValue={setMail}
						placeholder="Enter Mail Address"
					/>
				</DialogContent>
				<DialogActions>
					<CustomPrimaryButton
						onClick={handleSendInvitation}
						disabled={!isFormValid}
						label="send"
						additionalStyles={{
							marginLeft: '15px',
							marginRight: '15px',
							marginBottom: '10px',
						}}
					></CustomPrimaryButton>
				</DialogActions>
			</Dialog>
		</div>
	);
};

const mapActionsToProps = (dispatch) => {
	return {
		...getActions(dispatch),
	};
};

export default connect(null, mapActionsToProps)(AddFriendDialog);
