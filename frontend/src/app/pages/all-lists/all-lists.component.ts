import { Component, OnInit } from '@angular/core';
import { ListsComponent } from '../lists/lists.component';
import { DialogService } from 'primeng/dynamicdialog';
import { ListApiService } from '../../shared/services/list-api.service';
import { DataReloadTriggerService } from '../../shared/services/data-reload-trigger.service';
import { AlertService } from '../../shared/services/alert.service';

@Component({
  selector: 'app-all-lists',
  templateUrl: './all-lists.component.html',
  styleUrls: ['./all-lists.component.scss'],
})
export class AllListsComponent extends ListsComponent {
  constructor(
    dialogService: DialogService,
    listApiService: ListApiService,
    dataReloadTriggerService: DataReloadTriggerService,
    alertService: AlertService
  ) {
    super(
      dialogService,
      listApiService,
      dataReloadTriggerService,
      alertService
    );
  }

  override getLists() {
    this.listApiService.getAllLists().subscribe({
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
}
