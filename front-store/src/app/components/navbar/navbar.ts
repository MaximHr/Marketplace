import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { Searchbar } from './searchbar/searchbar';
import { RouterLink } from '@angular/router';
import { DropDown } from './dropdown/dropdown';
import { Cart } from './cart/cart';
import { Categroy } from '../../types/category';
import { ProductService } from '../../services/product-service';
import { handleError } from '../../services/errorHandler';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-navbar',
  styleUrl: 'navbar.css',
  templateUrl: 'navbar.html',
  imports: [Searchbar, RouterLink, DropDown, Cart],
})
export class Navbar implements OnInit {
  private service = inject(ProductService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private toastr = inject(ToastrService);

  categories = signal<Categroy[]>([]);

  isDropDownOpen = signal<boolean>(false);

  isCartOpen = signal<boolean>(false);

  isUserMenuOpen = signal<boolean>(false);
  isLoggedIn = computed(() => !!this.authService.currentUserToken());

  cart = computed(() => this.service.cartState());
  cartItemsCount = computed(() => this.cart()?.totalItems ?? 0);

  ngOnInit() {
    this.service
      .listCategories()
      .subscribe({
        next: (categories) => {
          this.categories.set(categories);
        },
        error: (err) => handleError(err, this.toastr),
      });

    this.service.getShoppingCart();
  }

  toggleOpen() {
    this.isDropDownOpen.update((curr) => !curr);
  }

  toggleCart() {
    this.isCartOpen.update((curr) => !curr);
  }

  toggleUserMenu() {
    this.isUserMenuOpen.update((curr) => !curr);
  }

  logout() {
    this.authService.updateToken(null);

    this.isUserMenuOpen.set(false);
    this.router.navigate(['/']);
  }
}
