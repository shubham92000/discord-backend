import { composeWithDevTools } from 'redux-devtools-extension';
import { combineReducers, createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import authReducer from './reducers/authReducer';
import alertReducer from './reducers/alertReducer';
import friendsReducer from './reducers/friendsReducer';
import chatReducer from './reducers/chatReducer';
import socketReducer from './reducers/socketReducer';

const rootReducer = combineReducers({
	auth: authReducer,
	alert: alertReducer,
	friends: friendsReducer,
	chat: chatReducer,
	socket: socketReducer,
});

const store = createStore(
	rootReducer,
	composeWithDevTools(applyMiddleware(thunk))
);

export default store;
