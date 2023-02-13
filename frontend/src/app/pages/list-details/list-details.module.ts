import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListDetailsComponent } from './list-details.component';
import { RouterModule, Routes } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { SharedModule } from '../../shared/shared.module';

const routes: Routes = [{ path: '', component: ListDetailsComponent }];
@NgModule({
  declarations: [ListDetailsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    InputTextModule,
    FormsModule,
    ButtonModule,
    SharedModule,
  ],
})
export class ListDetailsModule {}
