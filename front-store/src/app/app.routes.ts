import { Routes } from '@angular/router';
import { Landing } from './pages/landing/landing';
import { ProductsPage } from './pages/products/products';
import { Details } from './pages/details/details';
import { CategoriesPage } from './pages/categories/categories';
import { AddProduct } from './pages/add-product/add-product';

export const routes: Routes = [
	{
		path: '',
		component: Landing
	},
	{
		path: 'products',
		component: ProductsPage
	}, 
	{
		path: 'details/:slug',
		component: Details
	},
	{
		path: 'categories/:code',
		component: CategoriesPage
	},
	{
		path: 'add-product',
		component: AddProduct
	}
];
