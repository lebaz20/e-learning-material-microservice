import { Component, OnInit, Inject } from "@angular/core";
import { ProductRepository } from '../model/product.repository';
import { Product } from '../model/product.model';
import{HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { map, catchError, tap } from 'rxjs/operators';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog, MatSnackBar } from '@angular/material';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { Inventory } from '../model/inventory.model';

import { throwError } from 'rxjs';
import { RestService } from '../rest.service';
import { Router } from '@angular/router';

@Component({
    selector: "store",
    templateUrl: "store.component.html",
    styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {

    private products:any;

    private productsUrl = 'http://localhost:8000/product-service/products';

    constructor(private repository: ProductRepository,
                private http: HttpClient,
                private dialog: MatDialog) { }

    ngOnInit(): void {
        this.getProducts();
    }

    getProducts() {
        console.log("product api call")
        return this.http
        .get<Product[]>(this.productsUrl)
        .pipe(map(data => data)).subscribe(products => {
            this.products = products;
            console.log(this.products);
        })
    }

    openInventoryDialog() {
        const dialogRef = this.dialog.open(InventoryDialog, {
            width: '400px',
            height: '300px',
        });
    
        dialogRef.afterClosed().subscribe((showSnackBar: boolean) => {
          if (showSnackBar) {
            const a = document.createElement('a');
            a.click();
            a.remove();
          }
        });
    }

    openPriceDialog() {
        const dialogRef = this.dialog.open(PriceDialog, {
            width: '400px',
            height: '300px',
        });
    
        dialogRef.afterClosed().subscribe((showSnackBar: boolean) => {
            if (showSnackBar) {
              const a = document.createElement('a');
              a.click();
              a.remove();
            }
        });
    }

    openProductDialog() {
        const dialogRef = this.dialog.open(ProductDialog, {
            width: '400px',
            height: '400px'
        });
    
        dialogRef.afterClosed().subscribe((showSnackBar: boolean) => {
          if (showSnackBar) {
            const a = document.createElement('a');
            a.click();
            a.remove();
          }
        });
    }

    openProductEditDialog(product: Product) {
        console.log(product)
        const dialogRef = this.dialog.open(ProductEditDialog, {
            width: '400px',
            height: '400px',
            data: {
                product
            }
        });
    
        dialogRef.afterClosed().subscribe((showSnackBar: boolean) => {
          if (showSnackBar) {
            const a = document.createElement('a');
            a.click();
            a.remove();
          }
        });
    }

    openProductDeleteDialog(product: Product) {
        console.log(product)
        const dialogRef = this.dialog.open(ProductDeleteDialog, {
            width: '400px',
            height: '170px',
            data: {
                product
            }
        });
    
        dialogRef.afterClosed().subscribe((showSnackBar: boolean) => {
          if (showSnackBar) {
            const a = document.createElement('a');
            a.click();
            a.remove();
          }
        });
    }

    getProduct(id){
        console.log("product id ------------ " + id );
        console.log(this.productsUrl+"/"+id);
        return this.http
        .get<Product>(this.productsUrl+"/"+id)
        .pipe(map(data => data)).subscribe(products => {
            this.products = products;
            console.log(this.products);
        })
    }
}

@Component({
    selector: 'inventory-dialog',
    templateUrl: 'inventory.component.html',
})
export class InventoryDialog {

    inventory: Inventory = new Inventory();
    constructor(public dialogRef: MatDialogRef<InventoryDialog>,
                @Inject(MAT_DIALOG_DATA) public product: Product,
                private http: HttpClient,
                public rest: RestService,
                private router: Router) { }

    onNoClick() {
        console.log("cancel clicked")
        this.dialogRef.close();
    }

    updateInventory(form: NgForm) {
        console.log("updating inventory")
        console.log(this.inventory.productId);
        this.rest.updateInventory(this.inventory).subscribe((result) => {
            console.log("Inventory added successfully");
            window.location.reload();
            // this.router.navigate(['/store']);
        }, (err) => {
            console.log(err);
        });
        this.dialogRef.close(true);
    }
}


@Component({
    selector: 'price-dialog',
    templateUrl: 'price.component.html',
})
export class PriceDialog {

    product: Product = new Product();

    constructor(public dialogRef: MatDialogRef<PriceDialog>,
                @Inject(MAT_DIALOG_DATA) public productMatData: Product,
                private http: HttpClient,
                public rest: RestService,
                private router: Router) { }

    onNoClick(): void {
        console.log("cancel clicked")
        this.dialogRef.close(true);
    }

    addPrice(form: NgForm) {
        console.log("save price")
        console.log(this.product.id);
        this.rest.addPrice(this.product).subscribe((result) => {
            console.log("Price added successfully");
            window.location.reload();
          }, (err) => {
            console.log(err);
        });
        this.dialogRef.close(true);
    }
}


@Component({
    selector: 'product-dialog',
    templateUrl: 'product.component.html',
})
export class ProductDialog {
    baseUrl: string;
    product: Product = new Product();

    constructor(public dialogRef: MatDialogRef<ProductDialog>,
                @Inject(MAT_DIALOG_DATA) public productMatData: Product,
                private http: HttpClient,
                public rest: RestService,
                private router: Router) { }

    onNoClick(): void {
        console.log("cancel clicked")
        this.dialogRef.close();
    }

    addNewProduct(form: NgForm) {
        console.log("adding new product");
        console.log(this.product);
        this.rest.saveProduct(this.product).subscribe((result) => {
            console.log("product added successfully");
            window.location.reload();
            // this.router.navigate(['/store']);
          }, (err) => {
            console.log(err);
          });
        this.dialogRef.close(true);
    }    
}

@Component({
    selector: 'product-edit-dialog',
    templateUrl: 'product-edit.component.html',
})
export class ProductEditDialog {
    baseUrl: string;
    product: Product = new Product();

    constructor(@Inject(MAT_DIALOG_DATA) public data: any,
                public dialogRef: MatDialogRef<ProductDialog>,
                @Inject(MAT_DIALOG_DATA) public productMatData: Product,
                private http: HttpClient,
                public rest: RestService,
                private router: Router) { }

    onNoClick(): void {
        console.log("cancel clicked")
        this.dialogRef.close();
    }

    editProduct(form: NgForm) {
        console.log("updating product");
        console.log(this.data.product);
        this.rest.editProduct(this.data.product).subscribe((result) => {
            console.log("product updated successfully");
            window.location.reload();
            // this.router.navigate(['/store']);
          }, (err) => {
            console.log(err);
          });
        this.dialogRef.close(true);
    }    
}

@Component({
    selector: 'product-delete-dialog',
    templateUrl: 'product-delete.component.html',
})
export class ProductDeleteDialog {
    baseUrl: string;
    product: Product = new Product();

    constructor(@Inject(MAT_DIALOG_DATA) public data: any,
                public dialogRef: MatDialogRef<ProductDialog>,
                @Inject(MAT_DIALOG_DATA) public productMatData: Product,
                private http: HttpClient,
                public rest: RestService,
                private router: Router) { }

    onNoClick(): void {
        console.log("cancel clicked")
        this.dialogRef.close();
    }

    deleteProduct(form: NgForm) {
        console.log("deleting product");
        console.log(this.data.product);
        this.rest.deleteProduct(this.data.product).subscribe((result) => {
            console.log("product deleted successfully");
            window.location.reload();
            // this.router.navigate(['/store']);
          }, (err) => {
            console.log(err);
          });
        this.dialogRef.close(true);
    }    
}