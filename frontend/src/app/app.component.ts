import {Component, OnInit} from '@angular/core';
import {SCService} from './sc.service';
import {SC} from './sc';
import 'rxjs/Rx';
import {Router} from '@angular/router';
import {log} from "util";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  errorMessage: string;
  scs: SC[];
  sc: SC;

  constructor(private router: Router, private scService: SCService) {
  }

  ngOnInit() {
    this.scService.getSCs().subscribe(
      scs => this.scs = scs,
      error => this.errorMessage = <any> error);
  }

  addOne() {
    //window.open("temp.html");
    this.sc.id = null;
    this.sc.no = window.opener.document.getElementById("sc_no").value;
    this.sc.name = window.opener.document.getElementById("sc_name").value;
    this.sc.depart = window.opener.document.getElementById("sc_depart").value;
    this.sc.course = window.opener.document.getElementById("sc_course").value;
    this.sc.grade = null;
    this.scService.addSC(this.sc).subscribe(
      scs => this.scs = scs,
      error => this.errorMessage = <any> error);
  }

  importFile() {
    // if (this.is_insert) {
    //
    //   this.is_insert = false;
    // } else {
    //   this.is_insert = true;
    // }
  }

  delete(sc: SC) {

  }

  edit(sc: SC) {

  }

  grade(sc: SC) {

  }


}
