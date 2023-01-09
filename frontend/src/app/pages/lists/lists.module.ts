import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListsComponent } from './lists.component';
import { RouterModule, Routes } from '@angular/router';
import { AddListDialogComponent } from './components/add-list-dialog/add-list-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ColorPickerModule } from 'primeng/colorpicker';
import { ListCardComponent } from './components/list-card/list-card.component';
import { SharedModule } from '../../shared/shared.module';
import { MultiSelectModule } from 'primeng/multiselect';
import { ConfirmPopupModule } from 'primeng/confirmpopup';

const routes: Routes = [{ path: '', component: ListsComponent }];
@NgModule({
  declarations: [ListsComponent, AddListDialogComponent, ListCardComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    ColorPickerModule,
    SharedModule,
    MultiSelectModule,
    ConfirmPopupModule,
  ],
})
export class ListsModule {}
