import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../../service/product.service";
import {Product} from "../../../model/product";
import {ActivatedRoute} from '@angular/router';
import {CardService} from '../../../service/card.service';
import {CardItem} from '../../../model/card-item';
import {AuthService} from '../../../service/security/auth.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit{

  products: Product[]=[];

  messageAr: string="";
  messageEn: string="";

  page: number=1;
  pageLength: number=10;
  collectionSize: number=60;

  // for make delete
  selectedProduct: Product | null = null;
  showDeleteModal: boolean = false;




  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(() => this.loadProducts() );
  }

  constructor(private productService: ProductService ,private activatedRoute: ActivatedRoute
              , private cardService: CardService , private authService:AuthService){}

  loadProducts(){
    let hasCateortId =  this.activatedRoute.snapshot.paramMap.has("id");
    let hasProductName =  this.activatedRoute.snapshot.paramMap.has("key");
    if(hasCateortId){
      // this.activatedRoute.paramMap.subscribe(params => {
      //   const id = params.get("id");
      //   if (id) {
      //     this.getProductsByCategoryID(id);
      //   }
      // });
      this.getProductsByCategoryID(this.activatedRoute.snapshot.paramMap.get("id") , this.page , this.pageLength);
      return ;
    }
    if(hasProductName)
    {
      this.serachByName(this.activatedRoute.snapshot.paramMap.get("key"), this.page , this.pageLength);
      return ;
    }
    this.getProducts(this.page , this.pageLength);

  }


  getProducts(pageNumber , pageSize) {
    this.productService.getAllProducts(pageNumber , pageSize).subscribe(
      value => {
        this.products = value.products;
        this.collectionSize = value.totalProducts;
        // console.log(this.products)
        this.messageAr = "";
        this.messageEn = "";
        // console.log(this.products)
      },errorResponse =>{
        this.products =[];
        this.messageAr = errorResponse.error.bundleMessage.message_ar;
        this.messageEn = errorResponse.error.bundleMessage.message_en;
      }
    );
  }

  getProductsByCategoryID(id ,pageNumber , pageSize)
    {
      this.productService.getProductsByCategoryID(id ,pageNumber , pageSize).subscribe(
        value => {
          this.products = value.products;
          this.collectionSize = value.totalProducts;
          this.messageAr = "";
          this.messageEn = "";
          // console.log(this.products)
        },errorResponse =>{
          this.products =[];
          this.messageAr = errorResponse.error.bundleMessage.message_ar;
          this.messageEn = errorResponse.error.bundleMessage.message_en;
        }
      );
    }

  serachByName(word,pageNumber , pageSize)
  {
    this.productService.searchByProductName(word,pageNumber , pageSize).subscribe(
      value => {
        this.products = value.products;
        this.collectionSize = value.totalProducts;
        this.messageAr = "";
        this.messageEn = "";
        // console.log(this.products)
      },errorResponse =>{
        this.products =[];
        this.messageAr = errorResponse.error.bundleMessage.message_ar;
        this.messageEn = errorResponse.error.bundleMessage.message_en;
      }
    );
  }

  pageChange(){
    this.loadProducts();
    this.page=1;
  }

  changePageSize(event: Event) {
    this.pageLength = +(<HTMLInputElement>event.target).value;
    this.loadProducts()
  }

  addProduct(product: Product){
    let cardItem = new CardItem(product);
    this.cardService.addProduct(cardItem);
  }


  isUserAdmin(){
    return this.authService.isUserAdmin();
  }


  // for make Delete

  DeleteProduct(pro: Product) {
    this.selectedProduct = pro;
    this.showDeleteModal = true;
  }

  closeDeleteModal() {
    this.selectedProduct = null;
    this.showDeleteModal = false;
  }

  confirmDelete() {
    if (!this.selectedProduct)  {
      alert('No product selected!');
      return;
    }

    const productId = this.selectedProduct.id;

    this.productService.deleteProductById(productId).subscribe(
      res => {

        // this.products = this.products.filter(p => p.id !== this.selectedProduct.id);
        this.closeDeleteModal();
        alert(res);
        this.loadProducts();

      },
      err =>{
        this.closeDeleteModal();
        alert("Error:  " + err.message);
      }
    );

  }
  // this.activatedRoute.snapshot.paramMap.has("id");




}
