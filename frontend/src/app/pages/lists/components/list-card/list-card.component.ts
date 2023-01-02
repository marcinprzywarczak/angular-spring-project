import { Component, Input, OnInit } from '@angular/core';
import { ToDoList } from '../../../../shared/models/toDoList';
import { AddListDialogComponent } from '../add-list-dialog/add-list-dialog.component';
import { DialogService } from 'primeng/dynamicdialog';
import { ListApiService } from '../../../../shared/services/list-api.service';
import { DataReloadTriggerService } from '../../../../shared/services/data-reload-trigger.service';
import { AlertService } from '../../../../shared/services/alert.service';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-list-card',
  templateUrl: './list-card.component.html',
  styleUrls: ['./list-card.component.scss'],
})
export class ListCardComponent implements OnInit {
  @Input() list: ToDoList;
  hoverBgColor: string;
  hoverTextColor: string;
  hover: boolean = false;
  editButtonHover: boolean = false;
  deleteButtonHover: boolean = false;
  constructor(
    private dialogService: DialogService,
    private listApiService: ListApiService,
    private dataReloadTriggerService: DataReloadTriggerService,
    private alertService: AlertService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.hoverBgColor = this.hexToRGB(this.list.color, 0.8);
    this.hoverTextColor = this.hexToRGB(this.list.text_color, 0.7);
  }

  hexToRGB(hex: string, alpha: number) {
    const r = parseInt(hex.slice(1, 3), 16),
      g = parseInt(hex.slice(3, 5), 16),
      b = parseInt(hex.slice(5, 7), 16);

    if (alpha) {
      return 'rgba(' + r + ', ' + g + ', ' + b + ', ' + alpha + ')';
    } else {
      return 'rgb(' + r + ', ' + g + ', ' + b + ')';
    }
  }

  edit() {
    console.log('edit');
    this.dialogService.open(AddListDialogComponent, {
      header: 'Update list',
      width: '55rem',
      data: {
        edit: true,
        toDoList: this.list,
      },
    });
  }

  delete() {
    this.listApiService.deleteList(this.list.id).subscribe({
      next: (res) => {
        this.alertService.showSuccess('List successfully deleted!');
        this.dataReloadTriggerService.triggerListReload();
      },
      error: (err) => {},
    });
    console.log('delete');
  }

  confirm(event: Event) {
    this.confirmationService.confirm({
      target: event.target!,
      message: 'Are you sure that you want to delete this list?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.delete();
      },
    });
  }
}
