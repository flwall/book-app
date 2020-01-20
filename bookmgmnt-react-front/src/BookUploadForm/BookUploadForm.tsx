import "antd/dist/antd.css";
import "./BookUploadForm.css";
import React, { Component, FormEvent } from "react";

import { Form, Input, Button, Rate } from "antd";
import TextArea from "antd/lib/input/TextArea";
import { postBook } from "../redux/api-middleware";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";
import axios from "axios";
import { BASE_URL } from "../redux/api-constants";

class BookUploadForm extends Component<any, any> {
  handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const { postBook } = this.props;
    postBook(this.state.book);

    console.log("Form Submitted");
  }
  rating: number = 0;
  constructor(props: any) {
    super(props);

    this.cancel = this.cancel.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    if (!this.props.location.state) {
      this.red = true;
    } else {
      let title: string = this.props.location.state.fn;
      title = title.substring(0, title.lastIndexOf("."));
      this.state = {
        book: {
          id: 0,
          title: title,
          author: { name: "" },
          description: "",
          rating: 0,
          timestamp: this.props.location.state.timestamp
        },
        file: null
      };
    }
  }
  cancel(ev: React.MouseEvent<HTMLElement, MouseEvent>) {
    
    axios
      .post(BASE_URL + "cancel", { timestamp: this.state.book.timestamp })
      .then(ms => {})
      .catch(e => console.error(e));
    this.red = true;
    this.forceUpdate();
  }
  private red: boolean = false;
  fileChanged(e: React.ChangeEvent<HTMLInputElement>) {
    this.setState({ file: e.target.files![0] });
  }
  render() {
    if (this.red) return <Redirect to="/" />;
    return (
      <div className="bookuploadform">
        <h3 className="upload-title">Add a new Book</h3>

        <Form
          layout="horizontal"
          autoComplete="off"
          onSubmit={this.handleSubmit}
        >
          <Form.Item>
            <Input
              className="book-inp"
              placeholder="Book Title"
              name="title"
              value={this.state.book.title}
              onChange={e => {
                const { book } = Object.assign({}, this.state);
                book.title = e.target.value;
                this.setState({ book: book });
              }}
            />
          </Form.Item>
          <TextArea
            rows={4}
            cols={35}
            className="book-inp"
            id="book-desc"
            value={this.state.book.description}
            placeholder="Book Description"
            onChange={e => {
              let { book } = Object.assign({}, this.state);
              book.description = e.target.value;
              this.setState({ book: book });
            }}
          />
          <br />
          <Input
            type="text"
            placeholder="Author name"
            id="book-authorname"
            value={this.state.book.author.name}
            onChange={e =>
              this.setState({
                ...this.state,
                book: {
                  ...this.state.book,
                  author: { ...this.state.book.author, name: e.target.value }
                }
              })
            }
            className="book-inp"
          />
          <div id="book-rat"> Rating (1-5): </div>
          <Rate
            allowClear={false}
            value={this.state.book.rating}
            onChange={e =>
              this.setState({
                ...this.state,
                book: { ...this.state.book, rating: e }
              })
            }
          />
          <br />

          <Button className="book-cancel" onClick={this.cancel}>
            Cancel
          </Button>
          <Button type="primary" htmlType="submit">
            Add Book
          </Button>
        </Form>
      </div>
    );
  }
}

export default connect(null, { postBook })(BookUploadForm);
