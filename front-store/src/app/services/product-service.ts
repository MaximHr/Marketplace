import { HttpClient } from "@angular/common/http";
import { inject, Service } from "@angular/core";
import { environment } from "../environment";
import type { ProductCardT } from "../types/product-card";
import { PageResponse } from "../types/page-response";

@Service()
export class ProductService {
	private http = inject(HttpClient);
	private path = environment.serverUrl + `/products`;

	listProducts = () => {
		return this.http.get<PageResponse<ProductCardT>>(this.path);
	}
}