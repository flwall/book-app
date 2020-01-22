export interface Book {
  id: number;
  title: string;
  description: string;
  author: Author;
  rating: number;
  timestamp:string;     //number as string
  tags:Array<string>;
}
export interface Author {
  name: string;
}
