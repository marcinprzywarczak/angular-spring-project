import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ToDoList } from '../models/toDoList';

@Injectable({
  providedIn: 'root',
})
export class ListApiService {
  constructor(private http: HttpClient) {}

  getAllLists() {
    let headers = new HttpHeaders({
      Accept: 'application/json',
      'Content-type': 'application/json',
    });
    return this.http.get<ToDoList[]>(`${environment.apiUrl}/api/toDoList`, {
      headers: headers,
    });
  }

  addNewList(toDoList: any) {
    return this.http.post<ToDoList>(
      `${environment.apiUrl}/api/toDoList/add`,
      toDoList
    );
  }

  getList(id: number) {
    return this.http.get<ToDoList>(`${environment.apiUrl}/api/toDoList/${id}`);
  }

  updateList(toDoList: any, listId: number) {
    return this.http.put(
      `${environment.apiUrl}/api/toDoList/update/${listId}`,
      toDoList
    );
  }

  deleteList(listId: number) {
    return this.http.delete(
      `${environment.apiUrl}/api/toDoList/delete/${listId}`
    );
  }

  addNewItem(toDoListItem: any, toDoListId: number) {
    return this.http.post(
      `${environment.apiUrl}/api/toDoList/${toDoListId}/addNewItem`,
      toDoListItem
    );
  }

  checkItemAsDone(itemId: number, isDone: boolean) {
    return this.http.post(
      `${environment.apiUrl}/api/toDoList/${itemId}/checkAsDone`,
      isDone
    );
  }

  deleteItem(itemId: number) {
    return this.http.delete(
      `${environment.apiUrl}/api/toDoList/deleteItem/${itemId}`
    );
  }
}
