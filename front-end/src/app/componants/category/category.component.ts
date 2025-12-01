import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../service/category.service";
import {Category} from "../../../model/category";
import {SharedServiceService} from '../../../service/shared-service.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit{

  categories: Category[] = [];

  selectedCategory = 'ALL';

  constructor(private categoryService: CategoryService, private sharedService: SharedServiceService) {}


  ngOnInit(): void {
    this.sharedService.selectedCategory$.subscribe(cat => {
      this.selectedCategory = cat;
    });

    this.getCategories();
  }


  getCategories(){
    this.categoryService.getAllCategories().subscribe(
      value => this.categories = value
    );
  }

}
