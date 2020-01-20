export interface Book {
  id: number;
  title: string;
  description: string;
  author: Author;
  rating: number;
  timestamp:string;     //number as string
  formats:Array<string>;
}
export interface Author {
  name: string;
}
