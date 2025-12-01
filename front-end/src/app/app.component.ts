import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../service/security/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private authService:AuthService , private router:Router) {

  }

  isUserLogin(): boolean{
    return this.authService.isUserLogin();
  }

  isProductRoute(): boolean {
    return this.router.url.startsWith('/products') || this.router.url.startsWith('/category/');
  }

}
