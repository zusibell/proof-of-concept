import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http'
import { OverviewData } from './my-table.api';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';


const httpOptions: { headers: HttpHeaders } = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'responseType': 'text' as 'json',
    'Access-Control-Allow-Origin': '*'
  }),
};

@Injectable({
  providedIn: 'root'
})
export class MyTableService {

  constructor(private readonly http: HttpClient) { }

  public getOverviewData(): Observable<OverviewData[]> {
    return this.http.get<OverviewData[]>(`http://localhost:3107/api/home/overview`, httpOptions);
  }
}
