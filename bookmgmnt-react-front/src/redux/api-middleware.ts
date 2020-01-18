import {
  fetchBooksError,
  actionPending,
  fetchBooksSuccess,
  ADD_BOOK_SUCCESS,
  ADD_BOOK_ERROR,
  fetchBookSuccess
} from "./actions";
import { ALL_BOOKS, ADD_BOOK, BASE_URL } from "./api-constants";
import { Book } from "./model";

export function fetchBooksIfNotFetched() {
  return (dispatch: any, getState: any) => {
    if (getState().books === null) return dispatch(fetchBooks());

    return Promise.resolve();
  };
}
export function fetchBooks() {
  return async (dispatch: any) => {
    dispatch(actionPending());
    try {
      let fetched = await fetch(ALL_BOOKS);
      let books = await fetched.json();

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
    try {
      const rawResp = await fetch(ADD_BOOK, {
        method: "POST",
        body: JSON.stringify(book)
      });

      if (rawResp.ok) {
        dispatch({ type: ADD_BOOK_SUCCESS });
      } else
        dispatch({
          type: ADD_BOOK_ERROR,
          error: JSON.parse(await rawResp.json())
        });
    } catch (e) {
      dispatch({ type: ADD_BOOK_ERROR, error: e });
    }
  };
}
