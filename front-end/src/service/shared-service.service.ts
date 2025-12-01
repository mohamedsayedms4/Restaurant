import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedServiceService {

  // Default category is ALL
  private selectedCategorySource = new BehaviorSubject<string>('ALL');

  selectedCategory$ = this.selectedCategorySource.asObservable();

  setSelectedCategory(cat: string) {
    this.selectedCategorySource.next(cat);
  }
}
