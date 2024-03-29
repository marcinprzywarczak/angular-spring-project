import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './shared/guards/auth.guard';
import { ForbiddenPageComponent } from './pages/forbidden-page/forbidden-page.component';

const routes: Routes = [
  {
    path: 'register',
    loadChildren: () =>
      import('./pages/register/register.module').then((m) => m.RegisterModule),
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./pages/login/login.module').then((m) => m.LoginModule),
  },
  {
    path: 'users',
    loadChildren: () =>
      import('./pages/users/users.module').then((m) => m.UsersModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'lists',
    loadChildren: () =>
      import('./pages/lists/lists.module').then((m) => m.ListsModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'allLists',
    loadChildren: () =>
      import('./pages/all-lists/all-lists.module').then(
        (m) => m.AllListsModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'changePassword',
    loadChildren: () =>
      import('./pages/change-password/change-password.module').then(
        (m) => m.ChangePasswordModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'list/:id',
    loadChildren: () =>
      import('./pages/list-details/list-details.module').then(
        (m) => m.ListDetailsModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'forbidden',
    component: ForbiddenPageComponent,
  },
  {
    path: '',
    loadChildren: () =>
      import('./pages/lists/lists.module').then((m) => m.ListsModule),
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
