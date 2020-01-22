import "antd/dist/antd.css";

import React, { Component } from "react";
import { fetchBooksIfNotFetched } from "../redux/api-middleware";
import { connect } from "react-redux";

import Search from "antd/lib/input/Search";
import { Book } from "../redux/model";
import { FETCH_BOOKS_SUCCESS } from "../redux/actions";
import { bindActionCreators } from "redux";

class BookSearch extends Component<any, any> {
  constructor(props: any) {
    super(props);
    this.search = this.search.bind(this);
  }
  componentDidMount() {
    const { fetchBooks } = this.props;
    fetchBooks();
  }
  search(value: string) {
    const { setBooks } = this.props;
    if (value.length < 1) {
      setBooks(null);

      this.props.fetchBooks();
      return;
    }

    const books: Array<Book> = this.props.books;
    setBooks(
      books.filter(b => {
        let searches = value.split(" ");

        //SEARCH OPTIMIZATION NEEDED

        for (let search of searches) {
          if (b.title.indexOf(search) !== -1) {
            return true;
          }
        }
        return false;
      })
    );
    
  }
  render() {
    return (
      <div className="booksearch">
        <Search
          placeholder="input search text"
          onSearch={this.search}
          enterButton
        />
      </div>
    );
  }
}
const mapStateToProps = (state: any) => ({
  books: state.books,
  pending: state.pending
});
const mapDispatchToProps = (dispatch: any) => {
  return Object.assign(
    bindActionCreators(
      {
        fetchBooks: fetchBooksIfNotFetched
      },
      dispatch
    ),
    {
      setBooks: (books: Array<Book>) => {
        dispatch({ type: FETCH_BOOKS_SUCCESS, payload: books });
      }
    }
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(BookSearch);
