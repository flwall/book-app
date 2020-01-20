import "antd/dist/antd.css";
import "./BookDetailView.css";

import React, { Component } from "react";
import { connect } from "react-redux";

import { fetchBooksIfNotFetched } from "../redux/api-middleware";
import { Redirect, Link } from "react-router-dom";

import { Book } from "../redux/model";
import { Button, Radio } from "antd";
import { BASE_URL } from "../redux/api-constants";

class BookDetailView extends Component<any, any> {
  constructor(props: any) {
    super(props);

    const { id } = this.props.match.params;

    const idnum = parseInt(id);
    this.id = idnum;

    this.componentDidMount = this.componentDidMount.bind(this);
    this.download = this.download.bind(this);
    this.state = {
      format: ""
    };
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

  componentWillReceiveProps() {
    if (!this.props.pending) {
      this.setState({ format: this.props.book(this.id).formats[0] });
    }
  }
  render() {
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

    const fmts = book.formats.map((fmt, idx) => (
      <Radio value={fmt} key={idx}>
        {fmt}
      </Radio>
    ));

    return (
      <div className="bookdetailview">
        <h2 className="title">{book.title}</h2>

        <Radio.Group
          value={this.state.format}
          onChange={chg => this.setState({ format: chg.target.value })}
        >
          {fmts}
        </Radio.Group>

        <Button onClick={this.download}>Download Book</Button>
      </div>
    );
  }
  download() {
    fetch(BASE_URL + "books/download", {
      method: "POST",
      body: JSON.stringify({ id: this.id, format: this.state.format }),
      headers: { "Content-Type": "application/json" }
    })
      .then(response => {
        console.log(response);
        response.headers.forEach(h => console.log(`Header ${h}`));
        const filename = response.headers
          .get("Content-Disposition")
          ?.split("filename=")[1];
        console.log(filename);
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

export default connect(mapStateToProps, { fetchBooks: fetchBooksIfNotFetched })(
  BookDetailView
);
