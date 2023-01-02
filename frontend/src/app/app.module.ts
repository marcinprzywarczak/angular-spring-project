import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpClientModule,
  HttpHandler,
} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { httpInterceptorProviders } from './shared/interceptors/http-request.interceptor';
import { AuthInterceptor } from './shared/interceptors/auth.interceptor';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DialogService } from 'primeng/dynamicdialog';
import { ConfirmPopupModule } from 'primeng/confirmpopup';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ToastModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    ConfirmPopupModule,
  ],
  providers: [
    httpInterceptorProviders,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    MessageService,
    DialogService,
    ConfirmationService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
