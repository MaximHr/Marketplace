import { Component, input, inject, computed, signal } from '@angular/core';
import { ProductService } from '../../../services/product-service';
import { environment } from '../../../environment';
import { RouterLink } from '@angular/router';

@Component({
	selector: 'cart',
	imports: [RouterLink],
	templateUrl: 'cart.html'
})
export class Cart {
  private service = inject(ProductService);

  url = signal(environment.imageStorage);
  cartTotalAmount = computed(() => this.service.cartState()?.totalAmount?? 0);
  cartItems = computed(() => this.service.cartState()?.cartItems ?? []);

	close = input.required<() => void>();

	remove(productId: number) {
    this.service.removeItemFromCart(productId);
  }
}
