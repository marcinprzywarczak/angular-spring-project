import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClickStopPropagationDirective } from './directives/click-stop-propagation.directive';

@NgModule({
  declarations: [ClickStopPropagationDirective],
  exports: [ClickStopPropagationDirective],
  imports: [CommonModule],
})
export class SharedModule {}
