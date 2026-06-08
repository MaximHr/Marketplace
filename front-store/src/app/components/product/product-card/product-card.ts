import { Component, computed, input } from "@angular/core";
import { ProductCardT } from "../../../types/product-card";
import { environment } from '../../../environment';

@Component({
	selector: 'product-card',
	templateUrl: 'product-card.html'
})
export class ProductCard {
	product = input.required<ProductCardT>();

	url = computed(() => environment.imageStorage + this.product().mainImage);
};