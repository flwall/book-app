import {
  ACTION_PENDING,
  FETCH_BOOKS_SUCCESS,
  FETCH_BOOKS_ERROR,
  FETCH_BOOK_SUCCESS
} from "./actions";
import { initialState } from "./initialState";

export function booksReducer(state: any = initialState, action: any) {
  switch (action.type) {
    case ACTION_PENDING:
      return {
        ...state,
        books: [],
        pending: true,
        error: null
      };
    case FETCH_BOOKS_SUCCESS:
      const books = Array.from(action.payload);

      return {
        ...state,
        pending: false,
        books: books,
        error: null
      };
    case FETCH_BOOKS_ERROR:
      return {
        ...state,
        pending: false,
        books: [],
        error: action.error
      };
    case FETCH_BOOK_SUCCESS: {
      
      const book=Object.assign({}, action.payload);
      
      const arr=[];
      arr.push(book);
      
      return {
        ...state,
        pending: false,
        books: arr
      };
    }
    default:
      return state;
  }
}

export const getBooks = (state: any) => state.books;
export const getBooksPending = (state: any) => state.pending;
export const getBooksError = (state: any) => state.error;
