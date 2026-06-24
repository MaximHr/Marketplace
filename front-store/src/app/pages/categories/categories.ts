import { Component, computed, effect, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs/operators';

import { ProductCard } from '../../components/product/product-card/product-card';
import { ProductService } from '../../services/product-service';
import { ToastrService } from 'ngx-toastr';
import type { ProductCardT } from '../../types/product-card';
import { PageableRequest } from '../../types/pageable';
import { handleError } from '../../services/errorHandler';
import { PaginationController } from '../../components/pagination-controller/pagination-controller';
import { form, FormField } from '@angular/forms/signals';
import { environment } from '../../environment';

@Component({
  selector: 'categories-page',
  templateUrl: '../../components/product/products-grid.html',
  imports: [ProductCard, PaginationController, FormField],
})
export class CategoriesPage {
  private sortText = signal<'createdAt,desc' | 'price,desc' | 'price,asc'>(
    'createdAt,desc'
  );

  // read-only signal derived from route
  private categoryCode;

  constructor(
    private toastr: ToastrService,
    private service: ProductService,
    private route: ActivatedRoute,
  ) {
    this.categoryCode = toSignal(
      this.route.paramMap.pipe(
        map(params => params.get('code'))
      ),
      { initialValue: null }
    );

    effect((onCleanup) => {
      const params = {
        ...this.pageInfo(),
        sort: this.sortText()
      };
			const code = this.categoryCode();

			if (!code) {
				return;
			}

      const sub = this.service.listProductsByCategory(params, code).subscribe({
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

  title = computed<string>(() => this.categoryCode() || "")

  selectForm = form(this.sortText);

  pageInfo = signal<PageableRequest>({
    page: 0,
    size: environment.pageSize,
  });
}