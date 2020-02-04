import "antd/dist/antd.css";
import React, { Component } from "react";
import BookView from "./BookView/BookView";
import BookDetailView from "./BookView/BookDetailView";
import { Provider } from "react-redux";
import store from "./redux/store";
import BookUploadForm from "./BookUploadForm/BookUploadForm";

import {
  Switch,
  Route,
  BrowserRouter as Router,
  Redirect
} from "react-router-dom";
import { Menu, Icon, Upload, Spin, message } from "antd";
import { BASE_URL } from "./redux/api-constants";
import { RcFile } from "antd/lib/upload";
import BookSearch from "./BookSearch/BookSearch";

interface AppState {
  uploading: boolean;
  fn: string | null;
  timestamp: string;
}
export default class App extends Component<any, AppState> {
  constructor(props: any) {
    super(props);
    this.handleUpload = this.handleUpload.bind(this);
    this.state = { uploading: false, fn: null, timestamp: "" };
  }

  handleUpload(f: RcFile): boolean {

    let data = new FormData();
    data.append("bookFile", f);
    let timestamp = Date.now().toString();
    data.append("timestamp", timestamp);
    this.setState({ uploading: true, fn: null });
    fetch(BASE_URL + "/upload", { method: "post", body: data })
      .then(resp => {
        this.setState({ uploading: false, fn: f.name, timestamp: timestamp });
      })
      .catch(err => {
        console.log(err);
        message.error("Failed to upload Book");
        this.setState({ uploading: false });
      });

    return false;
  }
  render() {
    return (
      <Provider store={store}>
        <Router>
          {this.state.fn ? (
            <Redirect
              to={{
                pathname: "/upload",
                state: { fn: this.state.fn, timestamp: this.state.timestamp }
              }}
            />
          ) : (
            ""
          )}
          <Menu mode="horizontal">
            <Menu.Item key="upload">
              <Upload
                beforeUpload={this.handleUpload}
                showUploadList={false}
                accept=".pdf,.txt,.mobi,.epub"
              >
                <Icon type="upload" />
                Upload Book
              </Upload>
            </Menu.Item>
            <Menu.Item key="search">
              <BookSearch />
            </Menu.Item>
          </Menu>
          {this.state.uploading ? <Spin /> : ""}

          <Switch>
            <Route exact path="/" component={BookView} key={2}/>

            <Route path="/books/:id" component={BookDetailView} key={3}/>
            <Route path="/upload" component={BookUploadForm} key={4} />
            <Route path="/" key={5}>
              <Redirect to="/" />{" "}
            </Route>
          </Switch>
        </Router>
      </Provider>
    );
  }
}
