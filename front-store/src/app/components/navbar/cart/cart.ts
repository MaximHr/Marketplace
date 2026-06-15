import { Component, input } from '@angular/core';

@Component({
	selector: 'cart',
	templateUrl: 'cart.html',
})
export class Cart {
	close = input.required<() => void>();
}
