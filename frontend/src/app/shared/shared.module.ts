import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClickStopPropagationDirective } from './directives/click-stop-propagation.directive';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { ClickOutsideDirective } from './directives/click-outside.directive';
import {
  NgxPermissionsModule,
  NgxPermissionsRestrictStubModule,
} from 'ngx-permissions';
import { ListCardComponent } from './components/list-card/list-card.component';
import { AddListDialogComponent } from './components/add-list-dialog/add-list-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ColorPickerModule } from 'primeng/colorpicker';
import { MultiSelectModule } from 'primeng/multiselect';
import { ButtonModule } from 'primeng/button';

@NgModule({
  declarations: [
    ClickStopPropagationDirective,
    NavbarComponent,
    ClickOutsideDirective,
    ListCardComponent,
    AddListDialogComponent,
  ],
  exports: [
    ClickStopPropagationDirective,
    NavbarComponent,
    ClickOutsideDirective,
    ListCardComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    NgxPermissionsModule,
    ReactiveFormsModule,
    InputTextModule,
    ColorPickerModule,
    MultiSelectModule,
    ButtonModule,
  ],
})
export class SharedModule {}
