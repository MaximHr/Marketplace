import { Component } from "@angular/core";
import { Hero } from "../../components/hero/hero";
import { ProductIsle } from "../../components/product/product-isle/product-isle";
import { ProductCardT } from '../../types/product-card';

@Component({
	selector: 'landing',
	templateUrl: 'landing.html',
	imports: [Hero, ProductIsle]
})
export class Landing {

};