import { Component, Input, OnInit } from '@angular/core';
import { ListApiService } from '../../services/list-api.service';
import { DataReloadTriggerService } from '../../services/data-reload-trigger.service';
import { AlertService } from '../../services/alert.service';

@Component({
  selector: 'app-list-item',
  templateUrl: './list-item.component.html',
  styleUrls: ['./list-item.component.scss'],
})
export class ListItemComponent implements OnInit {
  @Input() item: any;
  @Input() index: number;
  value: string;
  edit: boolean = false;
  constructor(
    private listApiService: ListApiService,
    private dataReloadTriggerService: DataReloadTriggerService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.value = this.item.name;
  }

  checkAsDone(id: number, done: boolean) {
    this.listApiService.checkItemAsDone(id, done).subscribe({
      next: (res) => {
        this.dataReloadTriggerService.triggerListItemsReload();
      },
      error: (err) => {
        this.alertService.showError('Error while updating item.');
      },
    });
  }

  deleteItem(id: number) {
    this.listApiService.deleteItem(id).subscribe({
      next: (res) => {
        this.dataReloadTriggerService.triggerListItemsReload();
      },
      error: (err) => {
        this.alertService.showError('Error while deleting item.');
      },
    });
  }

  editItem(id: number) {
    this.edit = true;
  }

  updateItem(id: number) {
    this.listApiService.updateItem(id, this.value).subscribe({
      next: (res) => {
        this.item.name = this.value;
        this.edit = false;
      },
      error: (err) => {
        this.alertService.showError('Error while updating item.');
      },
    });
  }
}
