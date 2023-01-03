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

@NgModule({
  declarations: [
    ClickStopPropagationDirective,
    NavbarComponent,
    ClickOutsideDirective,
  ],
  exports: [
    ClickStopPropagationDirective,
    NavbarComponent,
    ClickOutsideDirective,
  ],
  imports: [CommonModule, RouterModule, NgxPermissionsModule],
})
export class SharedModule {}
