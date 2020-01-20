import {
  fetchBooksError,
  actionPending,
  fetchBooksSuccess,
  ADD_BOOK_SUCCESS,
  ADD_BOOK_ERROR,
  fetchBookSuccess
} from "./actions";
import axios from "axios";
import { ALL_BOOKS, ADD_BOOK, BASE_URL } from "./api-constants";
import { Book } from "./model";

export function fetchBooksIfNotFetched() {
  return (dispatch: any, getState: any) => {
    if (getState().books === null || getState().books === undefined) {
      console.log("FETCHING BOOKS");
      return dispatch(fetchBooks());
    }

    console.log("ALREADY FETCHED");
    return Promise.resolve();
  };
}
export function fetchBooks() {
  return async (dispatch: any) => {
    dispatch(actionPending());
    try {
      let fetched = await fetch(ALL_BOOKS);
      let books = await fetched.json();

      console.log("BOOKS FETCHED");
      dispatch(fetchBooksSuccess(books));
      return books;
    } catch (e) {
      dispatch(fetchBooksError(e));
    }
  };
}
export function fetchBook(id: number) {
  return async (dispatch: any) => {
    dispatch(actionPending());
    try {
      let fetched = await fetch(BASE_URL + "books/" + id);
      let book = await fetched.json();
      dispatch(fetchBookSuccess(book));
      return book;
    } catch (e) {
      dispatch(fetchBooksError(e));
    }
  };
}

export function uploadFile(f: File) {
  let data = new FormData();
  data.append("file", f);
}

export function postBook(book: Book) {
  return async (dispatch: any) => {
    dispatch(actionPending());
    axios
      .post<Book>(ADD_BOOK, JSON.stringify(book), {
        headers: { "Content-Type": "application/json" }
      })
      .then(res => {
        if (res.status > 199 && res.status <= 299) {
          dispatch({ type: ADD_BOOK_SUCCESS, payload: res.data });
        } else {
          console.log("Response code !=2xx at posting Book");
        }
      })
      .catch(err => dispatch({ type: ADD_BOOK_ERROR, error: err }));
  };
}
