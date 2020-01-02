import React from "react";
import "./App.css";

import BookView from "./BookView/BookView";
import { Provider } from "react-redux";
import store from "./redux/store";

const App: React.FC = () => {
  return (
    <Provider store={store}>
      <BookView  />
    </Provider>
  );
};

export default App;
