import { Component } from "@angular/core";
import { Searchbar } from "./searchbar/searchbar";
import { RouterLink } from "@angular/router";

@Component({
	selector: 'app-navbar',
	templateUrl: 'navbar.html',
	imports: [Searchbar, RouterLink]
})
export class Navbar {

};