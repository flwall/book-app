import React, { Component } from "react";
import "./App.css";

import BookView from "./BookView/BookView";
import BookDetailView from "./BookView/BookDetailView";
import { Provider } from "react-redux";
import store from "./redux/store";
import BookUploadForm from "./BookUploadForm/BookUploadForm";
import { Switch, Route, BrowserRouter as Router, Redirect } from "react-router-dom";

export default class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <Switch>
            <Route exact path="/">
              <BookView /> <br /> <BookUploadForm />
            </Route>
            <Route path="/books/:id" component={BookDetailView} />
            <Route path="/"><Redirect to="/"/> </Route>
          </Switch>
        </Router>
      </Provider>
    );
  }
}
