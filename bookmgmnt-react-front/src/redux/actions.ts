import { Book } from "./model";
export const ADD_BOOK = "ADD_BOOK";

export const FETCH_BOOKS_SUCCESS = "FETCH_BOOKS_SUCCESS";
export const FETCH_BOOKS_PENDING = "FETCH_BOOKS_PENDING";
export const FETCH_BOOKS_ERROR = "FETCH_BOOKS_ERROR";
export function fetchBooksPending() {
  return {
    type: FETCH_BOOKS_PENDING
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
