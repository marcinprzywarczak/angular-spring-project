import { Component, OnInit } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { UserDialogComponent } from './components/user-dialog/user-dialog.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
})
export class UsersComponent implements OnInit {
  constructor(private dialogService: DialogService) {}

  ngOnInit(): void {}
  addNewUser() {
    this.dialogService.open(UserDialogComponent, {
      header: 'Add new user',
      width: '50rem',
      data: {
        edit: false,
      },
    });
  }
}
