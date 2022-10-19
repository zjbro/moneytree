import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router'
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { AppComponent } from './app.component';
import { TransactionlistComponent } from './components/transactionlist.component';
import { TransactionService } from './_services/transaction.service';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { httpInterceptorProviders } from './_helper/http.interceptor';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AddtransactionComponent } from './components/addtransaction/addtransaction.component';


const appRoutes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent},
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
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    HttpClientModule,
    MaterialModule,
    BrowserAnimationsModule
    
  ],
  providers: [ 
    TransactionService, 
    [httpInterceptorProviders]
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
