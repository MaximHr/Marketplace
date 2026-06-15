import { Component, input } from '@angular/core';
import { Categroy } from '../../../types/category';

@Component({
  selector: 'dropdown',
  templateUrl: 'dropdown.html',
})
export class DropDown {
  categories = input.required<Categroy[]>();

  toggleOpen = input.required<() => void>();

  close = () => {
		this.toggleOpen()();
  };
}
