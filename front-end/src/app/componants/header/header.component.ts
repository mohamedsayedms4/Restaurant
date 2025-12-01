import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../../service/security/auth.service';
import {SharedServiceService} from '../../../service/shared-service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

    constructor(private router : Router , private authService:AuthService , private sharedService: SharedServiceService) {

    }

    search(key){
      this.router.navigateByUrl('/search/'+key);
    }

    isUserLogin(): boolean{
      return this.authService.isUserLogin();
    }

  isUserAdmin(){
      return this.authService.isUserAdmin();
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl("/login")
  }

  navigateIfEmpty(value: string) {
    if (!value || value.trim() === '') {
      this.router.navigate(['/products']);
    }
  }

  //  for Restoran


  setActiveAll() {
    this.sharedService.setSelectedCategory('ALL');
  }


//    Go To page Add
//   GoToAddNewProduct(event: Event) {
//     event.preventDefault();  // يمنع المتصفح من تحميل href التقليدي
//     this.router.navigate(['/add-new-product']);
//   }


}
