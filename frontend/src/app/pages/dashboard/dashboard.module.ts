import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { ButtonModule } from 'primeng/button';

const routes: Routes = [{ path: '', component: DashboardComponent }];
@NgModule({
  declarations: [DashboardComponent],
  imports: [CommonModule, RouterModule.forChild(routes), ButtonModule],
})
export class DashboardModule {}
