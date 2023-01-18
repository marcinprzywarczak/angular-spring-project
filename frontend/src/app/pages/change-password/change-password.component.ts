import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../shared/services/auth.service';
import { AlertService } from '../../shared/services/alert.service';
import { RegisterUser } from '../../shared/models/registerUser';
import { UpdatePassword } from '../../shared/models/updatePassword';
import { Router } from '@angular/router';
import { confirmedValidator } from '../../shared/validators/cofirmed-validator';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent implements OnInit {
  form: FormGroup;
  loading: boolean = false;
  serverErrors: any[];
  submitted: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        oldPassword: [null, [Validators.required]],
        password: [null, [Validators.required]],
        matchingPassword: [null, [Validators.required]],
      },
      { validators: confirmedValidator }
    );
  }

  onSubmit() {
    this.submitted = true;
    this.serverErrors = [];
    if (this.form.invalid) return;
    this.loading = true;
    const data: UpdatePassword = this.form.value;
    this.authService.updatePassword(data).subscribe({
      next: (res) => {
        this.loading = false;
        console.log(res);
        this.form.reset();
        this.router.navigate(['/']).then(() => {
          this.alertService.showSuccess('Password successfully changed!');
        });
      },
      error: (error) => {
        this.loading = false;
        console.log(error);
        console.log('error', error.error.message);
        if (error.status === 400) {
          this.serverErrors = JSON.parse(error.error.message);
          console.log(JSON.parse(error.error.message));
        }
        this.alertService.showError(
          'Error while changing password. Pleas try  again.'
        );
      },
    });
  }
}
