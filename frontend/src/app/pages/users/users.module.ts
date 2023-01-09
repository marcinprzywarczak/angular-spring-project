import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users.component';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from './components/user-list/user-list.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { UserDialogComponent } from './components/user-dialog/user-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

const routes: Routes = [
  {
    path: '',
    component: UsersComponent,
    data: { permissions: { only: ['ROLE_ADMIN'] } },
  },
];
@NgModule({
  declarations: [UsersComponent, UserListComponent, UserDialogComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    TableModule,
    ButtonModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    FormsModule,
    DropdownModule,
    MultiSelectModule,
    ConfirmDialogModule,
  ],
})
export class UsersModule {}
