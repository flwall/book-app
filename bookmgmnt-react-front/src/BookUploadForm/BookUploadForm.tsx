import "antd/dist/antd.css";
import "./BookUploadForm.css";
import React, { Component, FormEvent } from "react";

import { Form, Input, Button, Rate } from "antd";
import TextArea from "antd/lib/input/TextArea";
import { postBook } from "../redux/api-middleware";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";

import { BASE_URL } from "../redux/api-constants";
import { Book } from "../redux/model";
import EditableTagGroup from "./EditableTagGroup";
interface UploadState {
  book: Book;
}
class BookUploadForm extends Component<any, UploadState> {
  handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const { postBook } = this.props;
    postBook(this.state.book);
    this.red = true;
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
          timestamp: this.props.location.state.timestamp,
          tags: []
        }
      };
    }
  }
  cancel(ev: React.MouseEvent<HTMLElement, MouseEvent>) {
    fetch(BASE_URL + "cancel", {
      method: "POST",
      body: JSON.stringify({ timestamp: this.state.book.timestamp }),
      headers: { "Content-Type": "application/json" }
    }).catch(err => console.log(err));

    this.red = true;
    this.forceUpdate();
  }
  private red: boolean = false;

  render() {
    if (this.red && !this.props.pending) return <Redirect to="/" />;
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
              onChange={e =>
                this.setState({
                  book: { ...this.state.book, title: e.target.value }
                })
              }
            />
          </Form.Item>
          <TextArea
            rows={4}
            cols={35}
            className="book-inp"
            id="book-desc"
            value={this.state.book.description}
            placeholder="Book Description"
            onChange={e =>
              this.setState({
                book: { ...this.state.book, description: e.target.value }
              })
            }
          />
          <br />
          <Input
            type="text"
            placeholder="Author name"
            id="book-authorname"
            value={this.state.book.author.name}
            onChange={e =>
              this.setState({
                book: {
                  ...this.state.book,
                  author: { ...this.state.book.author, name: e.target.value }
                }
              })
            }
            className="book-inp"
          />
          <div id="book-rat"> Rating (1-5): </div>
          <Rate style={{marginBottom:10}}
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

          <EditableTagGroup
            setTags={(tags: Array<string>) =>
              this.setState({ book: { ...this.state.book, tags: tags } })
            }
            tags={this.state.book.tags}
          />

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
const mapStateToProps = (state: any) => ({
  pending: state.pending
});

export default connect(mapStateToProps, { postBook })(BookUploadForm);
