import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';
import { DropDownAnimation } from '../../animations/dropdown-animation';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: [DropDownAnimation],
})
export class NavbarComponent implements OnInit {
  isLogged: boolean;
  user: User;
  userDropdown: boolean = false;
  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isLogged = this.userService.getState();
    this.user = this.userService.getUser();
    console.log(this.user);
  }

  logout() {
    this.authService.logout().subscribe(() => {
      localStorage.clear();
      window.location.reload();
    });
  }
}
