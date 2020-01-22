import "antd/dist/antd.css";
import "./BookDetailView.css";

import React, { Component } from "react";
import { connect } from "react-redux";

import { fetchBooksIfNotFetched, deleteBook } from "../redux/api-middleware";
import { Redirect, Link } from "react-router-dom";

import { Book } from "../redux/model";
import { Button, Tag } from "antd";
import { BASE_URL } from "../redux/api-constants";

class BookDetailView extends Component<any, any> {
  constructor(props: any) {
    super(props);

    const { id } = this.props.match.params;

    const idnum = parseInt(id);
    this.id = idnum;

    this.componentDidMount = this.componentDidMount.bind(this);
    this.download = this.download.bind(this);
    this.deleteClicked = this.deleteClicked.bind(this);
    this.state = { deleted: false };
  }

  private id: number;

  componentDidMount() {
    if (isNaN(this.id)) return;

    const { fetchBooks } = this.props;
    fetchBooks();
  }

  shouldComponentRender(): boolean {
    const { pending } = this.props;

    return !pending;
  }

  render() {
    if (this.state.deleted) {
      return <Redirect to={{ pathname: "/" }} />;
    }
    if (isNaN(this.id)) return <Redirect to="/" />;
    if (!this.shouldComponentRender()) {
      return <h4 style={{ textAlign: "center" }}>Loading...</h4>;
    }

    const book: Book = this.props.book(this.id);
    if (!book) {
      return (
        <div className="notfound">
          <h3>Book Not Found</h3>
          <Link to="/">Go Back to All Books</Link>
        </div>
      );
    }

    const tags = book.tags.map((t, id) => (
      <Tag key={id} closable={false}>
        {t}
      </Tag>
    ));

    return (
      <div className="book-det">
        <h2 className="book-det">{book.title}</h2>
        <div>
          <b>Author:</b>
          {book.author.name}
        </div>
        <br />
        <div>
          <b>Description:</b> <br />
          {book.description}
        </div>
        <br />
        <div>
          <b>Tags:</b>
          <br />
          {tags}
        </div>
        <br />

        <Button type="danger" onClick={this.deleteClicked}>
          Delete Book
        </Button>
        <br/>
        <Button className="book-det" type="primary" id="dlbtn" onClick={this.download}>
          Download Book
        </Button>
      </div>
    );
  }
  download() {
    fetch(BASE_URL + `books/${this.id}/download`, {
      method: "POST"
    })
      .then(response => {
        const filename = response.headers
          .get("Content-Disposition")
          ?.split("filename=")[1];

        response.blob().then(blb => {
          let url = window.URL.createObjectURL(blb);
          let a = document.createElement("a");
          a.href = url;
          a.download = filename!;
          a.click();
        });
      })
      .catch(err => console.log(err));
  }

  deleteClicked(e: React.MouseEvent<HTMLElement, MouseEvent>) {
    const { deleteBook } = this.props;
    deleteBook(this.props.book(this.id));
    this.setState({ deleted: true });
  }
  componentDidUpdate() {
    if (!this.props.pending && this.state.deleted) {
      //this.forceUpdate();
    }
  }
}

const mapStateToProps = (state: any) => {
  return {
    book: (id: number) => {
      return state.books.filter((e: Book) => {
        return e.id === id;
      })[0];
    },
    error: state.error,
    pending: state.pending
  };
};

export default connect(mapStateToProps, {
  fetchBooks: fetchBooksIfNotFetched,
  deleteBook: deleteBook
})(BookDetailView);
