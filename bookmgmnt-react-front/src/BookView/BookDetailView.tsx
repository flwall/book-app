import "./BookDetailView.css";
import React, { Component } from "react";
import { connect } from "react-redux";

import { fetchBooksIfNotFetched } from "../redux/api-middleware";
import { Redirect, Link } from "react-router-dom";

import { Book } from "../redux/model";

class BookDetailView extends Component<any, {}> {
  constructor(props: any) {
    super(props);
    
    const { id } = this.props.match.params;

    const idnum = parseInt(id);
    this.id = idnum;

    this.componentDidMount = this.componentDidMount.bind(this);
  }

  private id: number;
  
  
  componentDidMount(){
    if (isNaN(this.id)) return;

    const { fetchBooks } = this.props;
    fetchBooks();
  }

  shouldComponentRender(): boolean {
    const { pending } = this.props;

    return !pending;
  }
  render() {
    if (isNaN(this.id)) return <Redirect to="/" />;
    if (!this.shouldComponentRender()) {
      return <h4 style={{ textAlign: "center" }}>Loading...</h4>;
    }

    const book:Book = this.props.book(this.id);
    if (!book) {
      return (
        <div className="notfound">
          <h3>Book Not Found</h3>
          <Link to="/">Go Back to All Books</Link>
        </div>
      );
    }
    

    return (
      <div className="bookdetailview">
        <h2 className="title">{book.title}</h2>
      </div>
    );
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
