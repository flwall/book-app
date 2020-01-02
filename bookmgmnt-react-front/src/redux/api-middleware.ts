import {
  fetchBooksError,
  fetchBooksPending,
  fetchBooksSuccess
} from "./actions";
import { ALL_BOOKS } from "./api-constants";

function fetchBooks() {
  return async (dispatch: any) => {
    dispatch(fetchBooksPending());
    try {
      let fetched = await fetch(ALL_BOOKS);
      let books = await fetched.json();
      console.log(books);
      dispatch(fetchBooksSuccess(books));
      return books;
    } catch (e) {
      dispatch(fetchBooksError(e));
    }
  };
}

export default fetchBooks;
