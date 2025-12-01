import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "../model/category";
import {map} from "rxjs/operators";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  url = 'http://localhost:9090/products';

  constructor(private http: HttpClient) {}

  getAllProducts(pageNumber , pageSize): Observable<any>{
    return this.http.get<any>(this.url + "/all-products?page="+pageNumber+"&size="+pageSize).pipe(
      map(
        response => response
      )
    );
  }

  getProductsByCategoryID(id , pageNumber , pageSize): Observable<any>{
      return this.http.get<any>(this.url + "/all-ProductsByCategoryId/" + id+ "?page="+pageNumber+"&size="+pageSize).pipe(
      map(
        response => response
      )
    );
  }

  searchByProductName(word, pageNumber , pageSize): Observable<any>{
    return this.http.get<any>(this.url + "/search-all-products-by-key?key=" + word+ "&page="+pageNumber+"&size="+pageSize).pipe(
      map(
        response => response
      )
    );
  }

  // for Delete By id
  deleteProductById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/delete-product-id/${id}`);
  }

}
