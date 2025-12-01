import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  url = 'http://localhost:9090/order/create-order';

  constructor(private http: HttpClient) {}

  sendOrderToBack(orders): Observable<any>{
    // username,password they are  a key for file json (send to body)
    return this.http.post<any>( this.url ,{orders}).pipe(
      map(
        response => response
      )
    );
    }
}
