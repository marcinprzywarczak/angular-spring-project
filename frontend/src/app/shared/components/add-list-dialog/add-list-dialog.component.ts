import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  OnInit,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ListApiService } from '../../services/list-api.service';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AlertService } from '../../services/alert.service';
import { DataReloadTriggerService } from '../../services/data-reload-trigger.service';
import { ToDoList } from '../../models/toDoList';
import { User } from '../../models/user';
import { HttpClient } from '@angular/common/http';
import { UserApiService } from '../../services/user-api.service';

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
  submitted: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private listApiService: ListApiService,
    private ref: DynamicDialogRef,
    private alertService: AlertService,
    private dataReloadTriggerService: DataReloadTriggerService,
    private config: DynamicDialogConfig,
    private http: HttpClient,
    private userApiService: UserApiService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
    this.isEdit = this.config.data.edit;
    this.form = this.formBuilder.group({
      name: [null, [Validators.required, Validators.maxLength(255)]],
      color: ['#000000', [Validators.required]],
      description: [null, [Validators.required, Validators.maxLength(500)]],
      text_color: ['#000000', [Validators.required]],
      users: [null, [Validators.required]],
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
    } else {
      this.form.get('color')?.patchValue('#000000');
      this.form.get('text_color')?.patchValue('#ffffff');
    }
  }

  onSubmit() {
    console.log(this.form);
    this.submitted = true;
    if (this.form.invalid) return;
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

  loadUsers() {
    this.userApiService.getUsers().subscribe({
      next: (res) => {
        this.userOptions = res;
      },
      error: (err) => {
        this.alertService.showError(`${err.status} error while getting users`);
      },
    });
  }
}
