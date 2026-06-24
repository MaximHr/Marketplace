import { Component, effect, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductCard } from '../../components/product/product-card/product-card';
import { ProductService } from '../../services/product-service';
import { ToastrService } from 'ngx-toastr';
import type { ProductCardT } from '../../types/product-card';
import { PageableRequest } from '../../types/pageable';
import { handleError } from '../../services/errorHandler';
import { PaginationController } from "../../components/pagination-controller/pagination-controller";

@Component({
  selector: 'products-page',
  templateUrl: 'products.html',
  imports: [CommonModule, ProductCard, PaginationController]
})
export class ProductsPage {
  private readonly pageSize = 2;

  constructor(
    private toastr: ToastrService,
    private service: ProductService,
  ) {
    effect((onCleanup) => {
      const params = this.pageInfo();

      const sub = this.service.listProducts(params).subscribe({
        next: (products) => {
          this.products.set(products.content);
          this.totalElements.set(products.totalElements);
          this.totalPages.set(products.totalPages);
        },
        error: (err) => handleError(err, this.toastr),
      });

      onCleanup(() => sub.unsubscribe());
    });
  }

  products = signal<ProductCardT[]>([]);

  totalElements = signal<number>(0);

  totalPages = signal<number>(0);

  title = signal<string>('All products');

  pageInfo = signal<PageableRequest>({
    page: 0,
    size: this.pageSize,
  });
}
