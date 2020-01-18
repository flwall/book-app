export interface Book {
  id: number;
  title: string;
  description: string;
  author: Author;
  rating: number;
}
export interface Author {
  name: string;
}
