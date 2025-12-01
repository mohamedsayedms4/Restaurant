import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-order-code',
  templateUrl: './order-code.component.html',
  styleUrls: ['./order-code.component.css']
})
export class OrderCodeComponent implements OnInit {

  code:string="";
  totalElement:string="";
  totalPrice:string="";
  constructor(private route: ActivatedRoute ,private router: Router) { }

  ngOnInit(): void {
    let hasCode =  this.route.snapshot.paramMap.has("code");
    let hasTotalNumber =  this.route.snapshot.paramMap.has("totalNumber");
    let hasTotalPrice =  this.route.snapshot.paramMap.has("totalPrice");

    if(hasCode && hasTotalNumber && hasTotalPrice){
      this.code = this.route.snapshot.paramMap.get("code");
      this.totalElement = this.route.snapshot.paramMap.get("totalNumber");
      this.totalPrice = this.route.snapshot.paramMap.get("totalPrice");
      return;
    }
    // this.router.navigate(['/product'], { replaceUrl: true });
  }


  confirm()
  {
    this.router.navigate(['/product'], { replaceUrl: true });
  }

}
