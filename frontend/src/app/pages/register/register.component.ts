import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  form: FormGroup;
  constructor(private formBuilder: FormBuilder, private http: HttpClient) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: [],
      email: [],
      password: [],
      passwordConfirmation: [],
    });
  }

  onSubmit() {
    console.log('submit');
    this.http
      .post('http://localhost:8080/api/user/register', {
        name: this.form.get('name')?.value,
        email: this.form.get('email')?.value,
        password: this.form.get('password')?.value,
        matchingPassword: this.form.get('passwordConfirmation')?.value,
      })
      .subscribe((res) => {
        console.log(res);
      });
  }
}
