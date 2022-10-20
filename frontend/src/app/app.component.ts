import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './_services/auth.service';
import { StorageService } from './_services/storage.service';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  isLoggedIn = false;
  username?: string;


  constructor(private storageService: StorageService, private authService: AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.username = user.username;
    }
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();
        
        window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });
  }
}
