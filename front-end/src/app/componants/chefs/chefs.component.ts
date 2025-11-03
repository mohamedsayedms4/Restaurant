import { Component, OnInit } from '@angular/core';

import {ChefService} from '../../../service/chef.service';
import {Chef} from '../../../model/chef';

@Component({
  selector: 'app-chefs',
  templateUrl: './chefs.component.html',
  styleUrls: ['./chefs.component.css']
})
export class ChefsComponent implements OnInit {

  chefs: Chef[]=[];

  ngOnInit(): void {
    this.getChefs();
  }

  constructor(private chefService: ChefService){
  }

  getChefs(){
    this.chefService.getAllChefs().subscribe(
      value => {
        this.chefs = value;
        console.log(this.chefs)
      }
    );
  }
}
