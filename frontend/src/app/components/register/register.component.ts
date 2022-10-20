import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/_models/user.model';
import { AuthService } from 'src/app/_services/auth.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  hide = true
  form!: FormGroup; 
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.createForm()
  }

  onSubmit(): void {
    let user: User = this.form.value as User

    this.authService.register(user.username, user.password).subscribe({
      next: data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    });
  }

  createForm(){
    return this.fb.group({
      username: this.fb.control<string>('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(6), Validators.maxLength(32)])
    })
  }
}




