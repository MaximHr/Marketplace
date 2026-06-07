import { Component, input, signal } from '@angular/core';
import type { ProductCardT } from '../../../types/product-card';
import { ProductCard } from '../product-card/product-card';

@Component({
  selector: 'product-isle',
  templateUrl: 'product-isle.html',
  imports: [ProductCard],
})
export class ProductIsle {
  title = input.required<string>();

  products = signal<ProductCardT[]>([
    {
      name: 'Vegetables',
      price: 10,
      slug: '123',
      createdAt: '12/03/04',
      mainImage: 'assets/images/vegetables.png',
    },
    {
      name: 'Fruits',
      price: 15,
      slug: '1234',
      createdAt: '12/03/04',
      mainImage: 'assets/images/vegetables.png',
    },
    {
      name: 'Vegetables',
      price: 10,
      slug: '123',
      createdAt: '12/03/04',
      mainImage: 'assets/images/vegetables.png',
    },
    {
      name: 'Fruits',
      price: 15,
      slug: '1234',
      createdAt: '12/03/04',
      mainImage: 'assets/images/vegetables.png',
    }
  ]);

  isLoading = signal(true);
  err = signal<string | null>(null);
}
