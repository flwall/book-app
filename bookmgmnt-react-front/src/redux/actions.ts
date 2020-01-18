import { Book } from "./model";

export const FETCH_BOOKS_SUCCESS = "FETCH_BOOKS_SUCCESS";
export const ACTION_PENDING = "ACTION_PENDING";
export const FETCH_BOOK_SUCCESS="FETCH_BOOK_SUCCESS";
export const FETCH_BOOKS_ERROR = "FETCH_BOOKS_ERROR";
export const ADD_BOOK_SUCCESS="ADD_BOOK_SUCCESS";
export const ADD_BOOK_ERROR="ADD_BOOK_ERROR";

export function actionPending() {
  return {
    type: ACTION_PENDING
  };
}

export function fetchBookSuccess(book:Book){
  return {
    type: FETCH_BOOK_SUCCESS,
    payload:book
  };

}

export function fetchBooksSuccess(books: Book[]) {
  
  return {
    type: FETCH_BOOKS_SUCCESS,
    payload: books
  };
}

export function fetchBooksError(error: any) {
  return {
    type: FETCH_BOOKS_ERROR,
    error: error
  };
}
