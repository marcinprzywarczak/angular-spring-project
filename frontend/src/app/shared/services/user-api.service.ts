import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { environment } from '../../../environments/environment';
import { LazyLoadEvent } from 'primeng/api';
import { JavaPageable } from '../models/java-pageable';
import { RegisterUser } from '../models/registerUser';
import { AddNewUser } from '../models/addNewUser';
import { UpdateUser } from '../models/updateUser';

@Injectable({
  providedIn: 'root',
})
export class UserApiService {
  constructor(private http: HttpClient) {}

  getAllUsers(filters: LazyLoadEvent) {
    return this.http.post<JavaPageable<User>>(
      `${environment.apiUrl}/api/user/filterUser`,
      filters
    );
  }

  getUserRoles() {
    return this.http.get<{ id: number; name: string }[]>(
      `${environment.apiUrl}/api/user/roles`
    );
  }

  addNewUser(addNewUser: AddNewUser) {
    return this.http.post(
      `${environment.apiUrl}/api/user/addNewUser`,
      addNewUser
    );
  }

  updateUser(updateUser: UpdateUser) {
    return this.http.put(
      `${environment.apiUrl}/api/user/${updateUser.id}/updateUser`,
      updateUser
    );
  }

  deleteUser(id: number) {
    return this.http.delete(`${environment.apiUrl}/api/user/${id}/delete`);
  }

  getUsers() {
    return this.http.get<User[]>(`${environment.apiUrl}/api/user`);
  }
}
