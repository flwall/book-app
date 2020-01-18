import "antd/dist/antd.css";
import "./BookUploadForm.css";
import React, { Component, FormEvent } from "react";
import { Book } from "../redux/model";
import { Form, Input, Button, Upload, Icon, Rate, message } from "antd";
import TextArea from "antd/lib/input/TextArea";

interface Props {}
interface State {
  book: Book;
  file: File | null;
}

export default class BookUploadForm extends Component<Props, State> {
  handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    console.log("Form Submitted");
  }
  rating: number = 0;
  constructor(props: Readonly<{}>) {
    super(props);
    this.state = {
      book: {id:0, title: "", author: { name: "" }, description: "", rating: 0 },
      file: null
    };
  }
  fileChanged(e: React.ChangeEvent<HTMLInputElement>) {
    this.setState({ file: e.target.files![0] });
  }
  render() {
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
          <div id="book-upload"> Select Book to upload: </div>

          <Upload
            className="book-inp"
            accept=".pdf,.txt,.mobi,.epub"
            name="bookFile"
            action="http://localhost:8585/upload"
            data={{ timestamp: Date.now() }}
            beforeUpload={(f, fl) => {
              this.uploadCnt++;
              if (this.uploadCnt > 1) {
                message.error("Cannot upload multiple files");
                return false;
              }
              return true;
            }}
            showUploadList={false}
          >
            <Button>
              <Icon type="upload" />
              Click to upload a Book
            </Button>
          </Upload>
          <br />
          <Button type="primary" htmlType="submit">
            Add Book
          </Button>
        </Form>
      </div>
    );
  }

 private uploadCnt: number = 0;
}
