import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  OnInit,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ListApiService } from '../../../../shared/services/list-api.service';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AlertService } from '../../../../shared/services/alert.service';
import { DataReloadTriggerService } from '../../../../shared/services/data-reload-trigger.service';
import { ToDoList } from '../../../../shared/models/toDoList';

@Component({
  selector: 'app-add-list-dialog',
  templateUrl: './add-list-dialog.component.html',
  styleUrls: ['./add-list-dialog.component.scss'],
})
export class AddListDialogComponent implements OnInit, AfterViewInit {
  form: FormGroup;
  isEdit: boolean;
  list: ToDoList;

  constructor(
    private formBuilder: FormBuilder,
    private listApiService: ListApiService,
    private ref: DynamicDialogRef,
    private alertService: AlertService,
    private dataReloadTriggerService: DataReloadTriggerService,
    private config: DynamicDialogConfig
  ) {}

  ngOnInit(): void {
    this.isEdit = this.config.data.edit;
    this.form = this.formBuilder.group({
      name: [],
      color: [],
      description: [],
      text_color: [],
    });
    if (this.isEdit) {
      this.list = this.config.data.toDoList;
      this.form.patchValue(this.list);
    }
  }

  ngAfterViewInit() {
    if (this.isEdit) {
      this.form.patchValue(this.list);
    }
  }

  onSubmit() {
    console.log(this.form.value);
    if (this.isEdit) {
      this.updateList();
    } else {
      this.addNewList();
    }
  }

  addNewList() {
    this.listApiService.addNewList(this.form.value).subscribe({
      next: (res) => {
        this.ref.close();
        this.alertService.showSuccess('New list successfully added!');
        this.dataReloadTriggerService.triggerListReload();
        console.log('res', res);
      },
      error: (err) => {
        this.alertService.showError('Error while adding new list.');
        console.log('err', err);
      },
    });
  }

  updateList() {
    this.listApiService.updateList(this.form.value, this.list.id).subscribe({
      next: (res) => {
        this.ref.close();
        this.alertService.showSuccess('New list successfully added!');
        this.dataReloadTriggerService.triggerListReload();
        console.log('res', res);
      },
      error: (err) => {
        this.alertService.showError('Error while adding new list.');
        console.log('err', err);
      },
    });
  }
}
