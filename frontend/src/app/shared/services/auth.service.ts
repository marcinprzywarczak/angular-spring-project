import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { RegisterUser } from '../models/registerUser';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  BASE_API_URL = environment.apiUrl;
  constructor(private http: HttpClient) {}

  login(login: string, password: string) {
    return this.http.post<User>(`${this.BASE_API_URL}/api/auth/signin`, {
      login: login,
      password: password,
    });
  }

  logout() {
    return this.http.post(`${this.BASE_API_URL}/api/auth/signout`, {});
  }

  register(registerData: RegisterUser) {
    return this.http.post(
      `${this.BASE_API_URL}/api/auth/register`,
      registerData
    );
  }
}
