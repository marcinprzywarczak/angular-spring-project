import { Component, OnDestroy, OnInit } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { ListApiService } from '../../shared/services/list-api.service';
import { ToDoList } from '../../shared/models/toDoList';
import { DataReloadTriggerService } from '../../shared/services/data-reload-trigger.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../shared/services/alert.service';
import { AddListDialogComponent } from '../../shared/components/add-list-dialog/add-list-dialog.component';

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.scss'],
})
export class ListsComponent implements OnInit, OnDestroy {
  lists: ToDoList[];
  triggerSubscription: Subscription;
  constructor(
    private dialogService: DialogService,
    protected listApiService: ListApiService,
    private dataReloadTriggerService: DataReloadTriggerService,
    protected alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.getLists();
    this.listenOnTrigger();
  }

  ngOnDestroy() {
    this.triggerSubscription.unsubscribe();
  }

  listenOnTrigger() {
    this.triggerSubscription =
      this.dataReloadTriggerService.listReloadTrigger.subscribe(() => {
        this.getLists();
      });
  }

  openDialog() {
    this.dialogService.open(AddListDialogComponent, {
      header: 'New list',
      width: '55rem',
      data: {
        edit: false,
      },
    });
  }

  getLists() {
    this.listApiService.getLists().subscribe({
      next: (res) => {
        this.lists = res;
        console.log('res', res);
      },
      error: (err) => {
        this.alertService.showError('Error while getting lists.');
        console.log('err', err);
      },
    });
  }

  sendEmail() {
    this.listApiService.email().subscribe((res) => {
      console.log(res);
    });
  }
}
