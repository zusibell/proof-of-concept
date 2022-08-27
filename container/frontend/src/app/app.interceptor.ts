import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpResponse, HttpRequest, HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AppInterceptor implements HttpInterceptor {
    intercept(httpRequest: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log(httpRequest.url)
        //console.log(JSON.parse(JSON.stringify(httpRequest)));
        //httpRequest.params.set("client_id","container-frontend");
        return next.handle(httpRequest);
      }
}