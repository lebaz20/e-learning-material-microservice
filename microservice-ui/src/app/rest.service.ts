import { Injectable } from '@angular/core';
import{HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { Product } from './model/product.model';
import { Router } from '@angular/router';

const productsUrl = 'http://localhost:8000/product-service/products';
const inventoryUrl = 'http://localhost:8000/inventory-service/inventory';
const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
};

@Injectable({
    providedIn: 'root'
})
export class RestService {
    
    constructor(private http: HttpClient) {}

    saveProduct(product): Observable<any> {
        return this.http.post<any>(productsUrl, 
            JSON.stringify(product), httpOptions)
            .pipe(
                catchError(this.handleError<any>('addProduct'))
            );
    }

    editProduct(product): Observable<any> {
        console.log(product);
        return this.http.put<any>(productsUrl + "/" + product.id, 
            JSON.stringify(product), httpOptions)
            .pipe(
                catchError(this.handleError<any>('editProduct'))
            );
    }

    deleteProduct(product): Observable<any> {
        console.log(product);
        return this.http.delete<any>(productsUrl + "/" + product.id, httpOptions)
            .pipe(
                catchError(this.handleError<any>('editProduct'))
            );
    }

    addPrice(product): Observable<any> {
        console.log(product.id);
        console.log(product.price);
        return this.http.put<any>(productsUrl + "/addPrice?id="+product.id+"&price="+product.price, 
        JSON.stringify(product), httpOptions)
        .pipe(
            catchError(this.handleError<any>('addPrice'))
        );
    }

    updateInventory(inventory): Observable<any> {
        console.log(inventory.productId);
        console.log(inventory.quantity);
        return this.http.post<any>(inventoryUrl, 
            JSON.stringify(inventory), httpOptions)
            .pipe(
                catchError(this.handleError<any>('updateInventory'))
            );
    }

    private handleError<T> (operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
    
          // TODO: send the error to remote logging infrastructure
          console.error(error); // log to console instead
    
          // TODO: better job of transforming error for user consumption
          console.log(`${operation} failed: ${error.message}`);
    
          // Let the app keep running by returning an empty result.
          return of(result as T);
        };
    }
}