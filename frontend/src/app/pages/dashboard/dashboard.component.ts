import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  isLogged: boolean;
  name: string;
  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.name = this.userService.getName();
    this.isLogged = this.userService.getState();
    // this.http.get(`${environment.apiUrl}/api/user`).subscribe((res) => {
    //   console.log(res);
    // });
  }

  logout() {
    this.authService.logout().subscribe(() => {
      localStorage.clear();
      window.location.reload();
    });
  }
}
