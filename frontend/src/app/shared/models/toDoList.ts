import { User } from './user';

export interface ToDoList {
  id: number;
  name: string;
  color: string;
  users: User[];
  user: User;
  description: string;
  text_color: string;
  toDoListItemSet: any[];
}
