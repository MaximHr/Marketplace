import { Component, effect, signal } from '@angular/core';
import { ProductCard } from '../../components/product/product-card/product-card';
import { ProductService } from '../../services/product-service';
import { ToastrService } from 'ngx-toastr';
import type { ProductCardT } from '../../types/product-card';
import { PageableRequest } from '../../types/pageable';
import { handleError } from '../../services/errorHandler';
import { PaginationController } from '../../components/pagination-controller/pagination-controller';
import { form, FormField } from '@angular/forms/signals';

@Component({
  selector: 'products-page',
  templateUrl: 'products.html',
  imports: [ProductCard, PaginationController, FormField],
})
export class ProductsPage {
  private readonly pageSize = 2;

  private sortText = signal<'createdAt,desc' | 'price,desc' | 'price,asc'>('createdAt,desc');

  constructor(
    private toastr: ToastrService,
    private service: ProductService,
  ) {
    effect((onCleanup) => {
      const params = {
        ...this.pageInfo(),
        sort: this.sortText(),
      };

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

  selectForm = form(this.sortText);

  pageInfo = signal<PageableRequest>({
    page: 0,
    size: this.pageSize,
  });
}
