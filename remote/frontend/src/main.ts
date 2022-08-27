import { HttpClientModule } from '@angular/common/http';
import { enableProdMode, importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { RouterModule, Routes } from '@angular/router';

import { AppModule } from './app/app.module';
import { MyTableComponent } from './app/my-table/my-table.component';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

const routes: Routes = [
  {path: '', loadComponent: () => import('./app/my-table/my-table.component').then(mod => mod.MyTableComponent)},
    {path: '**', redirectTo: '', pathMatch: 'full'}
];

bootstrapApplication(MyTableComponent, {
  providers: [
    importProvidersFrom(
      RouterModule.forRoot(routes),
    ),
    importProvidersFrom(HttpClientModule)
  ]
})
  .catch(err => console.error(err));
