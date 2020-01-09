import React, { Component, FormEvent } from "react";
import store from '../redux/store';
import {postBook} from "../redux/api-middleware";
import {Book} from "../redux/model";
interface Props {}
interface State {
  bookTitle: string;
}

export default class BookUploadForm extends Component<Props, State> {
  handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    console.log("Form Submitted");
  }
  constructor(props: Readonly<{}>) {
    super(props);
    this.state = { bookTitle: "" };
  }

  render() {
    
    console.log(store.getState());
    return (
      <div className="bookuploadform">
        <form onSubmit={this.handleSubmit}>
          <label>Book Title: </label>
          <input
            type="text"
            value={this.state.bookTitle}
            onChange={evt => this.updateInput(evt)}
          />

          <input type="submit" value="Upload Book" />
        </form>
      </div>
    );
  }
  updateInput(event: React.ChangeEvent<HTMLInputElement>) {
    this.setState({ bookTitle: event.target.value });
  }
}
