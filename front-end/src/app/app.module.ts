import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {NgModule} from '@angular/core';
import {ProductsComponent} from './componants/products/products.component';
import {HeaderComponent} from './componants/header/header.component';
import {CategoryComponent} from './componants/category/category.component';
import {CardDetailsComponent} from './componants/card-details/card-details.component';
import {CardComponent} from './componants/card/card.component';
import {BrowserModule} from '@angular/platform-browser';
import {FooterComponent} from './componants/footer/footer.component';
import { ChefsComponent } from './componants/chefs/chefs.component';
import { ContactInfoComponent } from './componants/contact-info/contact-info.component';
import {APP_BASE_HREF} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './componants/login/login.component';
import { SignupComponent } from './componants/signup/signup.component';
import {AuthInterceptor} from '../service/inertceptor/auth.interceptor';
import {AuthGuard} from '../service/guard/auth.guard';
import {NoauthGuard} from '../service/guard/noauth.guard';
import {NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import { OrderCodeComponent } from './componants/order-code/order-code.component';
import { AddNewProductComponent } from './componants/add-new-product/add-new-product.component';
import {FormsModule} from '@angular/forms';

// routes: Routes = [];


// http://localhost:4200/bkhj
export const routes: Routes = [

  // http://localhost:4200/active
  {path: 'products', component: ProductsComponent , canActivate: [AuthGuard]},
  {path: 'cardDetails', component: CardDetailsComponent , canActivate: [AuthGuard]},
  {path: 'contact-info', component: ContactInfoComponent , canActivate: [AuthGuard]},
  {path: 'chefs', component: ChefsComponent , canActivate: [AuthGuard]},
  // list of products based on category
  {path: 'category/:id', component: ProductsComponent , canActivate: [AuthGuard] },
  {path: 'search/:key', component: ProductsComponent , canActivate: [AuthGuard]},

  {path: 'login', component: LoginComponent , canActivate: [NoauthGuard]},
  {path: 'signup', component: SignupComponent , canActivate: [NoauthGuard]},

  {path: 'order-code/:code/totalNumber/:totalNumber/totalPrice/:totalPrice', component: OrderCodeComponent , canActivate: [AuthGuard]},

  // add new product

  {path: 'add-new-product', component: AddNewProductComponent , canActivate: [AuthGuard]},
  {path: 'delete-product-id/:id', component: AddNewProductComponent , canActivate: [AuthGuard]},


  // http://localhost:4200/
  {path: '', redirectTo: '/products', pathMatch: 'full'},

  // if user enter thing without all routes
  {path: '**', redirectTo: '/products', pathMatch: 'full'}

];

/*
*   // http://localhost:4200/
  {path: '', component:OrderItemsComponent}
* */
@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    HeaderComponent,
    CategoryComponent,
    CardDetailsComponent,
    CardComponent,
    FooterComponent,
    ChefsComponent,
    ContactInfoComponent,
    LoginComponent,
    SignupComponent,
    OrderCodeComponent,
    AddNewProductComponent
  ],
  imports: [
    RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'}),
    BrowserModule,
    HttpClientModule,
    NgbPaginationModule,
    FormsModule
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' }
    ,{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
