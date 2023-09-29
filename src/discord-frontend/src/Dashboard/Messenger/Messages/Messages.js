import React, { useRef, useEffect } from 'react';
import { connect } from 'react-redux';
import { styled } from '@mui/system';
import MessagesHeader from './MessagesHeader';
import Message from './Message';
import DateSeparator from './DateSeparator';

const MainContainer = styled('div')({
	height: 'calc(100% - 60px)',
	overflow: 'auto',
	display: 'flex',
	flexDirection: 'column',
	alignItems: 'center',
});

const convertDateToHumanReadable = (date, format) => {
	const map = {
		mm: date.getMonth() + 1,
		dd: date.getDate(),
		yy: date.getFullYear().toString().slice(-2),
		yyyy: date.getFullYear(),
	};

	return format.replace(/mm|dd|yy|yyy/gi, (matched) => map[matched]);
};

const Messages = ({ chosenChatDetails, messages }) => {
	return (
		<MainContainer>
			<MessagesHeader name={chosenChatDetails ? chosenChatDetails.name : ''} />
			{messages.map((message, index) => {
				const sameAuthor = true;

				const sameDay = true;

				return (
					<div key={message._id} style={{ width: '97%' }}>
						{(!sameDay || index === 0) && (
							<DateSeparator
								date={convertDateToHumanReadable(
									new Date(message.date),
									'dd/mm/yy'
								)}
							/>
						)}
						<Message
							content={message.content}
							username={message.author}
							sameAuthor={sameAuthor}
							date={null}
							sameDay={sameDay}
						/>
					</div>
				);
			})}
		</MainContainer>
	);
};

const mapStateToProps = ({ chat }) => {
	return {
		...chat,
	};
};

export default connect(mapStateToProps)(Messages);
