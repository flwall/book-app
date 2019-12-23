import {
    createStore
} from 'redux';


import bookApp from './reducers';
const store = createStore(bookApp);

export default store;