import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../shared/services/auth.service';
import { RegisterUser } from '../../shared/models/registerUser';
import { AlertService } from '../../shared/services/alert.service';
import { confirmedValidator } from '../../shared/validators/cofirmed-validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  form: FormGroup;
  loading: boolean = false;
  serverErrors: any[];
  emailError: string;
  submitted: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        name: [null, [Validators.required]],
        email: [null, [Validators.required, Validators.email]],
        password: [null, [Validators.required]],
        matchingPassword: [null, [Validators.required]],
      },
      { validators: confirmedValidator }
    );
  }

  onSubmit() {
    console.log('submit');
    this.submitted = true;
    this.emailError = '';
    this.serverErrors = [];
    if (this.form.invalid) return;
    this.loading = true;
    const data: RegisterUser = this.form.value;
    this.authService.register(data).subscribe({
      next: (res) => {
        this.loading = false;
        console.log(res);
        this.form.reset();
        this.alertService.showSuccess(
          'Account successfully created! Now you can login to app'
        );
      },
      error: (error) => {
        this.loading = false;
        console.log(error);
        console.log('error', error.error.message);
        if (error.status === 409) {
          this.emailError = error.error.message;
        }
        if (error.status === 400) {
          this.serverErrors = JSON.parse(error.error.message);
          console.log(JSON.parse(error.error.message));
        }
        this.alertService.showError(
          'Error while creating new account. Pleas try  again.'
        );
      },
    });
  }
}
