import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AllListsComponent } from './all-lists.component';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
const routes: Routes = [
  {
    path: '',
    component: AllListsComponent,
    data: { permissions: { only: ['ROLE_ADMIN'] } },
  },
];
@NgModule({
  declarations: [AllListsComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    RouterModule.forChild(routes),
  ],
})
export class AllListsModule {}
