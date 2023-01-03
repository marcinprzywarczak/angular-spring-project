import { Injectable } from '@angular/core';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private isLogged: boolean;
  private name: string;
  private user: User;
  constructor() {
    this.isLogged = localStorage.getItem('isLogged') === 'true';
    this.name = localStorage.getItem('name') ?? '';
    this.user = JSON.parse(localStorage.getItem('user') || '[]');
  }

  getState() {
    return this.isLogged;
  }

  getName() {
    return this.name;
  }

  getUser() {
    return this.user;
  }
}
