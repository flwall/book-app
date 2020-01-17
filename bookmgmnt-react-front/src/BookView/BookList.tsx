import "antd/dist/antd.css";
import "./BookList.css";
import React, { Component } from "react";
import { Book } from "../redux/model";
import { Table, Rate } from "antd";

import { ColumnProps } from "antd/lib/table";
import { parse } from "path";

interface BookProps {
  books: Book[];
}
export default class BookList extends Component<BookProps, {}> {
  render() {
    const booksWithKeys: any[] = [];
    this.props.books.forEach((b, idx) => {
      let newone = Object.defineProperty(b, "key", { value: idx });
      booksWithKeys.push(newone);
    });
    const columns: ColumnProps<Book>[] = [
      { title: "Title", key: "title", dataIndex:"title" },
      { title: "Description", key: "description", dataIndex:"description" },
      {title:"Author", dataIndex:"author.name"},
      {title:"Rating", render:(txt, bk, idx)=>(<Rate disabled defaultValue={parseInt(txt)} />), dataIndex:"rating"}
    ];

    return (
      <div>
        <h2 className="list-header">All Books </h2>
        <Table  dataSource={booksWithKeys} columns={columns} />
      </div>
    );
  }


}
