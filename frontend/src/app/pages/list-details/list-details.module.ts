import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListDetailsComponent } from './list-details.component';
import { RouterModule, Routes } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';

const routes: Routes = [{ path: '', component: ListDetailsComponent }];
@NgModule({
  declarations: [ListDetailsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    InputTextModule,
    FormsModule,
    ButtonModule,
  ],
})
export class ListDetailsModule {}
