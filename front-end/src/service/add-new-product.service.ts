import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../model/product';
import {map} from 'rxjs/operators';


export interface CategorNew {
  id?: number;       // لو موجودة
  name?: string;     // لو هتعمل جديد
  logo?: string;
  flag?: string;
}

export interface ProductNew {
  name: string;
  image: string;
  description: string;
  price: number;
  category: CategorNew;
}

@Injectable({
  providedIn: 'root'
})
export class AddNewProductService {

  url: string = 'http://localhost:9090/products/add-new-produt'

  constructor(private http: HttpClient) {}

  // AddNewProduct(product: ProductNew): Observable<any> {
  //   // send product directly in body
  //   return this.http.post<any>(this.url, product).pipe(
  //     map(response => response)
  //   );
  // }

  AddNewProduct(product: ProductNew): Observable<any> {
    // Send product object directly as JSON
    return this.http.post<any>(this.url, product).pipe(
      map(response => response)
    );
  }
}
