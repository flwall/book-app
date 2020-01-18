import "antd/dist/antd.css";
import "./BookList.css";
import { Redirect } from "react-router-dom";
import React, { Component } from "react";
import { Book } from "../redux/model";
import { Table, Rate } from "antd";

import { ColumnProps, TableEventListeners } from "antd/lib/table";

interface BookProps {
  books: Book[];
}
interface BookState {
  bookdetailid: number | null;
}
export default class BookList extends Component<BookProps, BookState> {
  constructor(props: BookProps) {
    super(props);
    this.state = { bookdetailid: null };


  this.onRow=this.onRow.bind(this);  
  }
  
  render() {
    if (this.state.bookdetailid != null) {
      return <Redirect to={`/books/${this.state.bookdetailid}`} />;
    }
    const booksWithKeys: any[] = [];
    this.props.books.forEach((b, idx) => {
      let newone = Object.defineProperty(b, "key", { value: idx });
      booksWithKeys.push(newone);
    });
    const columns: ColumnProps<Book>[] = [
      { title: "Title", key: "title", dataIndex: "title" },
      { title: "Description", key: "description", dataIndex: "description" },
      { title: "Author", dataIndex: "author.name" },
      {
        title: "Rating",
        render: (txt, bk, idx) => (
          <Rate disabled defaultValue={parseInt(txt)} />
        ),
        dataIndex: "rating"
      }
    ];

    return (
      <div>
        <h2 className="list-header">All Books </h2>
        <Table
          onRow={this.onRow}
          dataSource={booksWithKeys}
          columns={columns}
        />
      </div>
    );
  }

  private onRow(rcd: Book, idx: number): TableEventListeners {
   const self=this;
    return {
      onClick: evt => {
        
        self.setState({ bookdetailid: rcd.id });
      }
    };
  }
  
  
}
