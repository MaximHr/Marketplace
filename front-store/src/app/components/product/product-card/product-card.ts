import { Component, input } from "@angular/core";
import { ProductCardT } from "../../../types/product-card";

@Component({
	selector: 'product-card',
	templateUrl: 'product-card.html'
})
export class ProductCard {
	product = input.required<ProductCardT>();
};