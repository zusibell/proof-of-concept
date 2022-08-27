import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, Injector, OnInit, ViewEncapsulation } from '@angular/core';
import { createCustomElement } from '@angular/elements';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { OverviewData } from './my-table.api';
import { MyTableService } from './my-table.service';

@Component({
  standalone:true,
  selector: 'app-my-table',
  templateUrl: './my-table.component.html',
  styleUrls: ['./my-table.component.css'],
  encapsulation: ViewEncapsulation.ShadowDom,
  imports:[CommonModule, RouterModule, HttpClientModule]
})
export class MyTableComponent implements OnInit {
  public places: OverviewData[];
  title = 'frontend';

  constructor(injector: Injector, private tableService: MyTableService){
    const webComponent = createCustomElement(MyTableComponent, { injector });
    customElements.define('my-private-table', webComponent);
  }

  ngOnInit(): void {
  }

  public fillTable() {
   this.tableService.getOverviewData().subscribe((data: OverviewData[]) => {
    this.places = data;
   });
  }

}
