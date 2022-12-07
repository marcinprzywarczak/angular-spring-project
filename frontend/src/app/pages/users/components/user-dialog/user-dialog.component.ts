import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../../shared/services/auth.service';
import { AlertService } from '../../../../shared/services/alert.service';
import { RegisterUser } from '../../../../shared/models/registerUser';
import { UserApiService } from '../../../../shared/services/user-api.service';
import { AddNewUser } from '../../../../shared/models/addNewUser';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { DataReloadTriggerService } from '../../../../shared/services/data-reload-trigger.service';

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
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private alertService: AlertService,
    private userApiService: UserApiService,
    private ref: DynamicDialogRef,
    private dataReloadTriggerService: DataReloadTriggerService
  ) {}

  ngOnInit(): void {
    this.setUserRoles();
    this.form = this.formBuilder.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required]],
      password: [null, [Validators.required]],
      matchingPassword: [null, [Validators.required]],
      role: [null],
    });
  }

  onSubmit() {
    const data: AddNewUser = this.form.value;
    if (this.form.invalid) return;
    console.log('submit');
    this.emailError = '';
    this.serverErrors = [];
    this.loading = true;
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
}
