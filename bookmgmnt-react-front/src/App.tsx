import React from "react";
import "./App.css";

import BookView from "./BookView/BookView";
import { Provider } from "react-redux";
import store from "./redux/store";
import BookUploadForm from "./BookUploadForm/BookUploadForm";

const App: React.FC = () => {
  return (
    <Provider store={store}>
      <BookUploadForm />
      <BookView />
    </Provider>
  );
};

export default App;
