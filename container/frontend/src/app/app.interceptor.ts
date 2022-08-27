import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpResponse, HttpRequest, HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AppInterceptor implements HttpInterceptor {
    intercept(httpRequest: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log(httpRequest.urlWithParams)
        console.log(httpRequest.body)
        console.log(httpRequest.context)
        console.log(httpRequest.headers)
        console.log(httpRequest.responseType)
        console.log(httpRequest.method)
        console.log(httpRequest.withCredentials)
        return next.handle(httpRequest);
      }
}