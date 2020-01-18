import React, { Component } from "react";

import { getBooks, getBooksError, getBooksPending } from "../redux/reducers";
import { connect } from "react-redux";
import { fetchBooksIfNotFetched } from "../redux/api-middleware";

import { Book } from "../redux/model";
import BookList from "./BookList";

interface BookProps {
  fetchBooks(): any;
  pending: boolean;
  error: any;
  books: Book[];
}

class BookView extends Component<BookProps, {}> {
  constructor(props: BookProps) {
    super(props);

    this.shouldComponentRender = this.shouldComponentRender.bind(this);
  }

  componentDidMount() {
    const { fetchBooks } = this.props;
    fetchBooks();
  }

  shouldComponentRender() {
    const { pending } = this.props;

    if (pending === true) return false;

    return true;
  }

  render() {
    const { books, error } = this.props;

    if (!this.shouldComponentRender()) {
      return <h4 style={{ textAlign: "center" }}>Loading Books...</h4>;
    }
    if (error !== null) {
      return <h4 style={{ textAlign: "center" }}>Failed to load Books</h4>;
    }

    return (
      <div className="book-list-wrapper">
        <BookList books={books} />
      </div>
    );
  }
}

const mapStateToProps = (state: any) => ({
  error: getBooksError(state),
  books: getBooks(state),
  pending: getBooksPending(state)
});

export default connect(mapStateToProps, { fetchBooks: fetchBooksIfNotFetched })(
  BookView
);
