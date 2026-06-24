import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { environment } from '../environment';
import type { ProductCardT } from '../types/product-card';
import { PageResponse } from '../types/page-response';
import { Categroy } from '../types/category';
import type { PageableRequest } from '../types/pageable';
import type { ProductDetails } from '../types/product-details';

@Service()
export class ProductService {
  private http = inject(HttpClient);
  private path = environment.serverUrl + `/products`;

  listProducts = (pageable: PageableRequest) => {
    let params = new HttpParams().set('page', pageable.page).set('size', pageable.size);

    if (pageable.sort) {
      params = params.set('sort', pageable.sort);
    }

    return this.http.get<PageResponse<ProductCardT>>(this.path, {
      params,
    });
  };

  listProductsByCategory = (pageable: PageableRequest, code: string) => {
		let params = new HttpParams().set('page', pageable.page).set('size', pageable.size);

    if (pageable.sort) {
      params = params.set('sort', pageable.sort);
    }

    return this.http.get<PageResponse<ProductCardT>>(this.path + `/type/${code}`, {
      params,
    });
  };

  getDetails = (slug: string) => {
    return this.http.get<ProductDetails>(this.path + `/${slug}`);
  };

  listCategories = () => {
    return this.http.get<Categroy[]>(this.path + '/categories');
  };
}
