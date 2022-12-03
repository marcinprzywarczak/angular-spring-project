import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'frontend';
  users: any = [];
  userName: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getUsers();
  }

  saveUser() {
    this.http
      .post('http://localhost:8080/api/user', {
        name: this.userName,
      })
      .subscribe((res) => {
        console.log('users', res);
        this.userName = '';
        this.getUsers();
      });
  }

  getUsers() {
    this.http.get('http://localhost:8080/api/user').subscribe((res) => {
      console.log('users', res);
      this.users = res;
    });
  }
}
