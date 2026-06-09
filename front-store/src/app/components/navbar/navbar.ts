import { Component, inject, OnInit, signal } from '@angular/core';
import { Searchbar } from './searchbar/searchbar';
import { RouterLink } from '@angular/router';
import { DropDown } from './dropdown/dropdown';
import { Categroy } from '../../types/category';
import { ProductService } from '../../services/product-service';
import { handleError } from '../../services/errorHandler';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-navbar',
  templateUrl: 'navbar.html',
  imports: [Searchbar, RouterLink, DropDown],
})
export class Navbar implements OnInit {
  private service = inject(ProductService);

  constructor(private toastr: ToastrService) {}

  categories = signal<Categroy[]>([]);

  isDropDownOpen = signal<boolean>(false);

  ngOnInit() {
    this.service
      .listCategories()
      .subscribe({
        next: (categories) => {
          this.categories.set(categories);
        },
        error: (err) => handleError(err, this.toastr),
      });
  }

  toggleOpen() {
    this.isDropDownOpen.update((curr) => !curr);
  }
}
