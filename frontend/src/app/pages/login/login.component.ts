import { Component } from '@angular/core';
import {AuthRequestBody} from "../../services/models/auth-request-body";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  authRequest:AuthRequestBody = {
    email: '',
    password: ''
  }

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {

  }

  errorMessages: string[] = [];

  login() {
    this.errorMessages = [];  // Clear error messages
    this.authService.login({
      body: this.authRequest
    }).subscribe({
      next:(response) => {
        // TODO: save the token in local storage

        // Redirect to books page
        this.router.navigate(['books']);

      },
      error: (error) => {
        console.log(error);
        this.errorMessages.push("Invalid email or password");
      }
    })
  }

  register() {
    this.router.navigate(['register']);
  }



}
