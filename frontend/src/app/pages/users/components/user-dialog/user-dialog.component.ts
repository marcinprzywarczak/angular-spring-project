import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../../shared/services/auth.service';
import { AlertService } from '../../../../shared/services/alert.service';
import { RegisterUser } from '../../../../shared/models/registerUser';
import { UserApiService } from '../../../../shared/services/user-api.service';
import { AddNewUser } from '../../../../shared/models/addNewUser';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { DataReloadTriggerService } from '../../../../shared/services/data-reload-trigger.service';
import { UpdateUser } from '../../../../shared/models/updateUser';

@Component({
  selector: 'app-user-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.scss'],
})
export class UserDialogComponent implements OnInit {
  form: FormGroup;
  loading: boolean = false;
  serverErrors: any[];
  emailError: string;
  userRolesOptions: { id: number; name: string }[];
  edit: boolean = false;
  user: any;
  submitted: boolean;
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private alertService: AlertService,
    private userApiService: UserApiService,
    private ref: DynamicDialogRef,
    private dataReloadTriggerService: DataReloadTriggerService,
    private config: DynamicDialogConfig
  ) {}

  ngOnInit(): void {
    this.edit = this.config.data.edit;
    if (this.edit) {
      this.user = this.config.data.user;
    }
    this.setUserRoles();
    const controlsConfig: any = this.edit
      ? {
          id: [null],
          name: [null, [Validators.required]],
          email: [null, [Validators.required, Validators.email]],
          roles: [null],
        }
      : {
          name: [null, [Validators.required]],
          email: [null, [Validators.required, Validators.email]],
          password: [null, [Validators.required]],
          matchingPassword: [null, [Validators.required]],
          roles: [null],
        };
    this.form = this.formBuilder.group(controlsConfig);
    this.form.patchValue(this.user);
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.invalid) return;

    this.emailError = '';
    this.serverErrors = [];
    this.loading = true;

    if (this.edit) {
      this.updateUser();
    } else {
      this.addNewUser();
    }
  }

  setUserRoles() {
    this.userApiService.getUserRoles().subscribe({
      next: (res) => {
        this.userRolesOptions = res;
      },
      error: (err) => {
        this.alertService.showError(
          `Error ${err.status} while getting user roles.`
        );
      },
    });
  }

  addNewUser() {
    const data: AddNewUser = this.form.value;

    this.userApiService.addNewUser(data).subscribe({
      next: (res) => {
        this.loading = false;
        this.form.reset();
        this.alertService.showSuccess('User successfully added!');
        this.ref.close();
        this.dataReloadTriggerService.triggerUserReload();
      },
      error: (error) => {
        this.loading = false;
        this.alertService.showError(
          `Error ${error.status} while adding new user.`
        );
        if (!error.error) return;
        if (error.status === 409) {
          this.emailError = error.error.message;
        }
        if (error.status === 400) {
          this.serverErrors = JSON.parse(error.error.message);
        }
      },
    });
  }

  updateUser() {
    const data: UpdateUser = this.form.value;

    this.userApiService.updateUser(data).subscribe({
      next: (res) => {
        this.loading = false;
        this.form.reset();
        this.alertService.showSuccess('User successfully updated!');
        this.ref.close();
        this.dataReloadTriggerService.triggerUserReload();
      },
      error: (error) => {
        this.loading = false;
        this.alertService.showError(
          `Error ${error.status} while updating new user.`
        );
        if (!error.error) return;
        if (error.status === 409) {
          this.emailError = error.error.message;
        }
        if (error.status === 400) {
          this.serverErrors = JSON.parse(error.error.message);
        }
      },
    });
  }
}
