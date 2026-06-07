import { Component } from "@angular/core";
import { Hero } from "../../components/hero/hero";

@Component({
	selector: 'landing',
	templateUrl: 'landing.html',
	imports: [Hero]
})
export class Landing {

};