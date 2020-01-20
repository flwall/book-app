import {
  ACTION_PENDING,
  FETCH_BOOKS_SUCCESS,
  FETCH_BOOKS_ERROR,
  FETCH_BOOK_SUCCESS,
  ADD_BOOK_SUCCESS,
  ADD_BOOK_ERROR
} from "./actions";
import { initialState } from "./initialState";
import { Book } from "./model";


export function booksReducer(state: any = initialState, action: any) {
  switch (action.type) {
    case ACTION_PENDING:
      return {
        ...state,
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
    case ADD_BOOK_SUCCESS:{
      const book:Book=action.payload;
      console.log(state);
      if(state.books===null){

        return {...state, books:[book],pending:false};
      }
      let bs:Array<Book>=state.books;
      bs.push(book);
      return {
        ...state, books:bs, pending:false
      }

    }
    case ADD_BOOK_ERROR:{
      return {...state, error:action.error};

    }
    default:
      return state;
  }
}

export const getBooks = (state: any) => state.books;
export const getBooksPending = (state: any) => state.pending;
export const getBooksError = (state: any) => state.error;
