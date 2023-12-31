import React, { useState } from 'react';
import CustomPrimaryButton from '../../shared/components/CustomPrimaryButton';
import AddFriendDialog from './AddFriendDialog';

const additionalStyles = {
	marginTop: '10px',
	marginLeft: '5px',
	width: '80%',
	height: '30px',
	background: '#3ba55d',
};

const AddFriendButton = () => {
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const handleOpenAddFriend = () => {
		setIsDialogOpen(true);
	};
	const handleCloseAddFriend = () => {
		setIsDialogOpen(false);
	};
	return (
		<>
			<CustomPrimaryButton
				additionalStyles={additionalStyles}
				label="Add Friend"
				onClick={handleOpenAddFriend}
			/>
			<AddFriendDialog
				isDialogOpen={isDialogOpen}
				closeDialogHandler={handleCloseAddFriend}
			/>
		</>
	);
};

export default AddFriendButton;
