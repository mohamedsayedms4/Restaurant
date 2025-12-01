import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../service/security/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  messageAr: string ="";
  messageEn: string ="";

  constructor(private authService:AuthService , private router:Router) { }

  ngOnInit(): void {
  }


  createAccount(userName , password , confirmPassword){
        if( !this.validateInputs(userName, password, confirmPassword) ){
          setTimeout(() => {
            this.messageAr = "";
            this.messageEn = "";
          }, 3000);
          return;
        }

        // call API
    this.authService.signup(userName,password).subscribe(
      response =>{
        // got to page products if signup success
        sessionStorage.setItem("token", response.token);
        sessionStorage.setItem("role", response.userRoles);
        this.router.navigateByUrl('/products');
      },errorRespo =>{
        console.log(errorRespo);
        this.messageAr = errorRespo.error.bundleMessage.message_ar;
        this.messageEn = errorRespo.error.bundleMessage.message_en;
        setTimeout(() => {
          this.messageAr = "";
          this.messageEn = "";
        }, 3000);
      }

    )
  }

  // validate in frontend
  validateInputs(userName , password , confirmPassword):boolean{
    if (!userName) {
      this.messageAr = "اسم المستخدم اجباري";
      this.messageEn = "Username is required";
      return false;
    }

    if (!password) {
      this.messageAr = "كلمة المرور اجبارية";
      this.messageEn = "Password is required";
      return false;
    }

    if (!confirmPassword) {
      this.messageAr = "تأكيد كلمة المرور اجباري";
      this.messageEn = "Confirm password is required";
      return false;
    }

    if (password !== confirmPassword) {
      this.messageAr = "كلمة المرور وتأكيدها غير متطابقين";
      this.messageEn = "Password and confirm password do not match";
      return false;
    }

    return true;
  }



}
