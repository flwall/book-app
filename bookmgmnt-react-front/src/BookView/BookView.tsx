import React, { Component } from "react";

import { getBooks, getBooksError, getBooksPending } from "../redux/reducers";
import { connect } from "react-redux";
import { fetchBooks } from "../redux/api-middleware";
import { bindActionCreators } from "redux";
import { Book } from "../redux/model";
import BookList from "./BookList";
import { message } from "antd";

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
      return "Loading Books...";
    }
    if (error !== null) {
      message.error("Failed to load books");
      return "Failed to load Books";
    }
    
    return (
      <div className="product-list-wrapper">
        {error && <span className="product-list-error">{error}</span>}
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

const mapDispatchToProps = (dispatch: any) =>
  bindActionCreators(
    {
      fetchBooks: fetchBooks
    },
    dispatch
  );

export default connect(mapStateToProps, mapDispatchToProps)(BookView);
