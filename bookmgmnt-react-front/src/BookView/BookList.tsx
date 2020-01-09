import React, { Component } from "react";
import { Book } from "../redux/model";

interface BookProps {
  books: Book[];
}
export default class BookList extends Component<BookProps, {}> {
  render() {
    return (
      <div>
        {this.props.books.map((item, idx) => (
          <p key={idx}>
            Title: {item.title}, Author: {item.author.name}
          </p>
        ))}
      </div>
    );
  }
}
