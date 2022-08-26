import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyTableComponent } from './my-table/my-table.component';

const routes: Routes = [
  {path: '', component: MyTableComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
