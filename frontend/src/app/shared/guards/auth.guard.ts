import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../services/user.service';
import { NgxPermissionsService } from 'ngx-permissions';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private userService: UserService,
    private router: Router,
    private ngxPermissionsService: NgxPermissionsService
  ) {}
  canActivate(): boolean {
    if (this.userService.getState()) {
      const user = this.userService.getUser();

      this.ngxPermissionsService.loadPermissions(user.roles);
    }
    if (!this.userService.getState()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
