import { Component, OnDestroy, OnInit } from '@angular/core';
import { User } from '../../../../shared/models/user';
import { UserApiService } from '../../../../shared/services/user-api.service';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { DataReloadTriggerService } from '../../../../shared/services/data-reload-trigger.service';
import { finalize, Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/services/alert.service';
import { DialogService } from 'primeng/dynamicdialog';
import { UserDialogComponent } from '../user-dialog/user-dialog.component';
import { UserService } from '../../../../shared/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit, OnDestroy {
  columns: string[] = ['id', 'name', 'email', 'roles'];
  users: User[];
  totalRecords: number;
  reloadDataTriggerSubscription: Subscription;
  lazyLoadEvent: LazyLoadEvent;
  loading: boolean;
  loggedUser: User;

  constructor(
    private userApiService: UserApiService,
    private dataReloadTriggerService: DataReloadTriggerService,
    private alertService: AlertService,
    private dialogService: DialogService,
    private confirmationService: ConfirmationService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loggedUser = this.userService.getUser();
    this.listenOnDataReload();
  }

  ngOnDestroy() {
    this.reloadDataTriggerSubscription.unsubscribe();
  }

  listenOnDataReload() {
    this.reloadDataTriggerSubscription =
      this.dataReloadTriggerService.userReloadTrigger.subscribe(() => {
        this.getUsers(this.lazyLoadEvent);
      });
  }

  getUsers(event: LazyLoadEvent) {
    this.loading = true;
    this.lazyLoadEvent = event;
    this.userApiService
      .getAllUsers(event)
      .pipe(
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (res) => {
          this.totalRecords = res.totalElements;
          this.users = res.content;
        },
        error: (err) => {
          this.alertService.showError(
            err.status + ' error while getting users.'
          );
        },
      });
  }

  editUser(user: any) {
    console.log('edit', user);
    let userEdit = Object.assign({}, user);
    userEdit.roles = userEdit.roles.map((role: any) => {
      return role.name;
    });
    this.dialogService.open(UserDialogComponent, {
      header: 'Edit user',
      width: '55rem',
      data: {
        edit: true,
        user: userEdit,
      },
    });
    console.log('edit', userEdit);
  }

  deleteUser(user: any) {
    this.confirmationService.confirm({
      message:
        'This action will delete user account and all his todo lists.<br> This action is irreversible',
      header: 'Are you sure that you want to delete this user?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.userApiService
          .deleteUser(user.id)
          .pipe(finalize(() => {}))
          .subscribe({
            next: (res) => {
              this.alertService.showSuccess('User successfully updated!');
              this.getUsers(this.lazyLoadEvent);
            },
            error: (err) => {
              if (err.error.message) {
                this.alertService.showError(
                  `${err.status} error. ${err.error.message}`
                );
              } else
                this.alertService.showError(
                  `${err.status} error while deleting user.`
                );
            },
          });
      },
    });
  }
}
