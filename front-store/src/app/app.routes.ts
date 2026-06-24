import { Routes } from '@angular/router';
import { Landing } from './pages/landing/landing';
import { ProductsPage } from './pages/products/products';
import { Details } from './pages/details/details';
import { CategoriesPage } from './pages/categories/categories';

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
	}
];
