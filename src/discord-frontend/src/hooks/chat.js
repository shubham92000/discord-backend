import React, { useRef, useEffect } from 'react';

export const useChatScroll = (messages) => {
	const ref = useRef();
	useEffect(() => {
		if (ref.current) {
			ref.current.scrollTop = ref.current.scrollHeight;
		}
	}, [messages]);
	return ref;
};
