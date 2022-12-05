import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../shared/services/auth.service';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  login: String;
  password: String;
  submitted: boolean = false;
  serverError: string;
  loading: boolean = false;
  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      login: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
    });
  }

  submit() {
    this.submitted = true;
    if (this.form.invalid) return;
    this.loading = true;
    this.serverError = '';
    this.authService
      .login(this.form.get('login')?.value, this.form.get('password')?.value)
      .pipe(
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (user) => {
          localStorage.setItem('isLogged', 'true');
          localStorage.setItem('user', user.email);
          localStorage.setItem('name', user.name);
          this.router.navigate(['']);
        },
        error: (err) => {
          if (err.status === 400) this.serverError = err.error.message;
        },
      });
  }
}
