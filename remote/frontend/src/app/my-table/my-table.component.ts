import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { OverviewData } from './my-table.api';
import { MyTableService } from './my-table.service';

@Component({
  selector: 'app-my-table',
  templateUrl: './my-table.component.html',
  styleUrls: ['./my-table.component.css'],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class MyTableComponent implements OnInit {
  public places: OverviewData[];

  constructor(private tableService: MyTableService) { }

  ngOnInit(): void {
  }

  public fillTable() {
   this.tableService.getOverviewData().subscribe((data: OverviewData[]) => {
    this.places = data;
   });
  }

}
