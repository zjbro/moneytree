import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router'
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { AppComponent } from './app.component';
import { TransactionService } from './_services/transaction.service';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { httpInterceptorProviders } from './_helper/http.interceptor';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { TransactionlistComponent } from './components/transactionlist/transactionlist.component';
import { HomeComponent } from './components/home/home.component';
import { AddtransactionComponent } from './components/addtransaction/addtransaction.component';


const appRoutes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'addtransaction', component: AddtransactionComponent },
  { path: 'transactionlist/:username', component: TransactionlistComponent},
  { path: '**', redirectTo: '/', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    AddtransactionComponent,
    TransactionlistComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    HttpClientModule,
    MaterialModule,
    BrowserAnimationsModule,
    FormsModule
    
  ],
  providers: [ 
    TransactionService, 
    [httpInterceptorProviders]
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
