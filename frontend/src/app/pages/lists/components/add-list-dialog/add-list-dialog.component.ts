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
import { User } from '../../../../shared/models/user';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-add-list-dialog',
  templateUrl: './add-list-dialog.component.html',
  styleUrls: ['./add-list-dialog.component.scss'],
})
export class AddListDialogComponent implements OnInit, AfterViewInit {
  form: FormGroup;
  isEdit: boolean;
  list: ToDoList;
  userOptions: User[];

  constructor(
    private formBuilder: FormBuilder,
    private listApiService: ListApiService,
    private ref: DynamicDialogRef,
    private alertService: AlertService,
    private dataReloadTriggerService: DataReloadTriggerService,
    private config: DynamicDialogConfig,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.http.get('http://localhost:8080/api/user').subscribe((res: any) => {
      this.userOptions = res;
    });
    this.isEdit = this.config.data.edit;
    this.form = this.formBuilder.group({
      name: [],
      color: [],
      description: [],
      text_color: [],
      users: [],
    });
    if (this.isEdit) {
      this.list = this.config.data.toDoList;
      this.form.patchValue(this.list);
      this.form.get('users')?.patchValue(
        this.list.users.map((user) => {
          return user.email;
        })
      );
    }
  }

  ngAfterViewInit() {
    if (this.isEdit) {
      this.form.patchValue(this.list);
      this.form.get('users')?.patchValue(
        this.list.users.map((user) => {
          return user.email;
        })
      );
    }
  }

  onSubmit() {
    console.log(this.form.value);
    // return;
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
        console.log('err', JSON.parse(err.error.message));
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
        if (err.status === 403) {
          this.alertService.showError(
            '403 Forbidden! You cant edit this data.'
          );
          this.ref.close();
        } else {
          this.alertService.showError('Error while adding new list.');
        }
        console.log('err', err);
      },
    });
  }
}
