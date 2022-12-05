import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private isLogged: boolean;
  private name: string;
  constructor() {
    this.isLogged = localStorage.getItem('isLogged') === 'true';
    this.name = localStorage.getItem('name') ?? '';
  }

  getState() {
    return this.isLogged;
  }

  getName() {
    return this.name;
  }
}
