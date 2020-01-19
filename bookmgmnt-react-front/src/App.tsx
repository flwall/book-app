import "antd/dist/antd.css";

import "./App.css";
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

export default class App extends Component<any,any> {
  constructor(props: any) {
    super(props);
    this.handleUpload = this.handleUpload.bind(this);
    this.state = { uploading:false, fn: null };
  }

  handleUpload(f: RcFile): boolean {
    let data = new FormData();
    data.append("bookFile", f);
    this.setState({uploading:true, fn:null})
    fetch(BASE_URL + "/upload", { method: "post", body: data }).then(resp => {
      this.setState({ uploading:false, fn: f.name });
    }).catch(err=>{console.log(err); message.error("Failed to upload Book"); this.setState({uploading:false})});

    return false;
  }
  render() {
    return (
      <Provider store={store}>
        <Router>
          {this.state.fn?<Redirect to={{pathname:"/upload", state:{fn:this.state.fn}}} />:""}
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
          </Menu>
          {this.state.uploading?<Spin/>:""}


          <Switch>
            <Route exact path="/">
              <BookView />
            </Route>
            <Route path="/books/:id" component={BookDetailView} />
            <Route path="/upload" component={BookUploadForm}/>
            <Route path="/">
              <Redirect to="/" />{" "}
            </Route>
            
          </Switch>
        </Router>
      </Provider>
    );
  }
}
