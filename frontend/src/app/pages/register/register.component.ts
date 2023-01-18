import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../shared/services/auth.service';
import { RegisterUser } from '../../shared/models/registerUser';
import { AlertService } from '../../shared/services/alert.service';

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
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: [],
      email: [],
      password: [],
      matchingPassword: [],
    });
  }

  onSubmit() {
    console.log('submit');
    this.emailError = '';
    this.serverErrors = [];
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
