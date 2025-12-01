import { Component, OnInit } from '@angular/core';
import {CategoryComponent} from '../category/category.component';
import {Category} from '../../../model/category';
import {CategoryService} from '../../../service/category.service';
import {AddNewProductService, ProductNew} from '../../../service/add-new-product.service';

@Component({
  selector: 'app-add-new-product',
  templateUrl: './add-new-product.component.html',
  styleUrls: ['./add-new-product.component.css']
})
export class AddNewProductComponent implements OnInit {

  categories: Category[] = [];
  flags: string[] = [];

  // Form fields
  productName: string = '';
  productImage: string = '';
  productDescription: string = '';
  productPrice: number = 0;
  selectedCategoryId: number | null = null;

  newCategoryName: string = '';
  newCategoryLogo: string = '';
  newCategoryFlag: string = '';

  constructor( private categoryService: CategoryService,
               private addNewProductService: AddNewProductService) { }

  ngOnInit(): void {
    this.getCategories();
  }



  getCategories(){
    this.categoryService.getAllCategories().subscribe(
      value => {
        this.categories = value.filter(cat => cat.name !== 'ALL');
        this.flags = [...new Set(this.categories.map(cat => cat.flag))];
      }
    );
  }

  SaveProduct() {
    const product: ProductNew = {
      name: this.productName,
      image: this.productImage,
      description: this.productDescription,
      price: this.productPrice,
      category: this.selectedCategoryId ? { id: this.selectedCategoryId } : {
        name: this.newCategoryName,
        logo: this.newCategoryLogo,
        flag: this.newCategoryFlag
      }
    };

    this.addNewProductService.AddNewProduct(product).subscribe(
      res => {
        alert('Product added successfully');
        // Reset form if needed
        this.resetForm();
      },
      err =>{ console.error('Error adding product', err) ;
        alert('Error adding product');
      }

    );


  }

  resetForm() {
    this.productName = '';
    this.productImage = '';
    this.productDescription = '';
    this.productPrice = 0;
    this.selectedCategoryId = null;
    this.newCategoryName = '';
    this.newCategoryLogo = '';
    this.newCategoryFlag = '';
  }

}
