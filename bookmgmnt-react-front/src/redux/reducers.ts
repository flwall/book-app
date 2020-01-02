import {
  FETCH_BOOKS_PENDING,
  FETCH_BOOKS_SUCCESS,
  FETCH_BOOKS_ERROR
} from "./actions";
import initialState from "./initialState";

export function booksReducer(state = initialState, action: any) {
  switch (action.type) {
    case FETCH_BOOKS_PENDING:
      return {
        ...state,
        pending: true
      };
    case FETCH_BOOKS_SUCCESS:
      console.log("FETCHED BOOKS SUCCESSFULLY");
      const books = Array.from(action.payload);
      console.log(books);
      return {
        ...state,
        pending: false,
        books: books
      };
    case FETCH_BOOKS_ERROR:
      console.log("ERROR FETCHING BOOKS" + action.error);
      return {
        ...state,
        pending: false,
        books: action.error
      };
    default:
      return state;
  }
}

export const getBooks = (state: any) => state.books;
export const getBooksPending = (state: any) => state.pending;
export const getBooksError = (state: any) => state.error;
