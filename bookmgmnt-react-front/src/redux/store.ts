import { createStore, Store, applyMiddleware } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import { booksReducer } from "./reducers";
import thunk from "redux-thunk";
import {initialState} from "./initialState";

const middlewares = [thunk];
const composeEnhancers = composeWithDevTools({});

const store: Store = createStore(
  booksReducer,
  initialState,
  composeEnhancers(applyMiddleware(...middlewares))
);

export default store;
