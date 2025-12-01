import {Component, OnInit} from '@angular/core';
import {CardItem} from '../../../model/card-item';
import {CardService} from '../../../service/card.service';
import {OrderService} from '../../../service/order.service';
import {BehaviorSubject} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit
{

  totalElementSize: number = 0;
  totalElementPrice: number = 0;
  items: CardItem[] = [];

  constructor(private cardService: CardService , private orderService:OrderService ,private router:Router) {
  }

  ngOnInit(): void {
    this.cardService.totalNumber.subscribe(value => {
      this.totalElementSize = value;
    })

    this.cardService.totalPrice.subscribe(value => {
      this.totalElementPrice = value;
    })
    this.items = this.cardService.cardItems;
  }

  addItem(item: CardItem) {
    this.cardService.addProduct(item)
  }

  removeItem(item: CardItem) {
    this.cardService.removeItem(item)
  }

  remove(item: CardItem) {
    this.cardService.remove(item)
  }

  takeOrder()
  {
    const requestOrderVm = this.cardService.cardItems.map(item=>
      ({
        id: item.id ,
        quantity: item.quantity
      })
    );
    // alert(JSON.stringify(requestOrderVm));

    this.orderService.sendOrderToBack(requestOrderVm).subscribe(
      response => {
        this.router.navigate(['/order-code/'+ response.code +'/totalNumber/'+
                            response.totalNumber+'/totalPrice/'+response.totalPrice], { replaceUrl: true });
      }
    );

    this.cardService.totalPrice.next(0);
    this.cardService.totalNumber.next(0);
    this.cardService.cardItems = [];


    // this.router.navigate(['/product'], { replaceUrl: true });
  }
}
