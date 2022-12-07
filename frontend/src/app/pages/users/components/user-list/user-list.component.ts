import { Component, OnDestroy, OnInit } from '@angular/core';
import { User } from '../../../../shared/models/user';
import { UserApiService } from '../../../../shared/services/user-api.service';
import { LazyLoadEvent } from 'primeng/api';
import { DataReloadTriggerService } from '../../../../shared/services/data-reload-trigger.service';
import { finalize, Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/services/alert.service';

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

  constructor(
    private userApiService: UserApiService,
    private dataReloadTriggerService: DataReloadTriggerService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
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
    console.log(event);
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
}
