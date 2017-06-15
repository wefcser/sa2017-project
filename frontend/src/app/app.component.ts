import {Component, OnInit, Input} from '@angular/core';
import {SCService} from './sc.service';
import {SC} from './sc';
import {SCImpl} from './scImpl';
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
  @Input() sc: SC;
  new_sc: SC;
  response_status: number;
  add_success: boolean = false;
  delete_success: boolean = false;

  constructor(private router: Router, private scService: SCService) {
  }

  ngOnInit() {
    this.scService.getSCs().subscribe(
      scs => this.scs = scs,
      error => this.errorMessage = <any> error);
  }

  addOne() {
    var sc:SC = new SCImpl();
    sc.id = 3;
    sc.no = document.getElementsByTagName("input")[0].value;
    sc.name = document.getElementsByTagName("input")[1].value;
    sc.depart = document.getElementsByTagName("input")[2].value;
    sc.course = document.getElementsByTagName("input")[3].value;
    this.scService.addSC(sc).subscribe(
      new_sc => {
        this.new_sc = new_sc;
        if(this.new_sc.no == "141220108"){window.open("temp.html");}
        this.add_success = true;
      },
      error => this.errorMessage = <any>error);
    this.closeDiv('AddOne');
  }

  // importFile() {
  //   // if (this.is_insert) {
  //   //
  //   //   this.is_insert = false;
  //   // } else {
  //   //   this.is_insert = true;
  //   // }
  // }

  delete(sc: SC) {
    this.scService.deleteSC(sc.id.toString()).subscribe(
      response => {
        this.response_status = response;
        if (this.response_status === 204) {
          this.delete_success = true;
          this.sc = <SC>{};
        }
      },
      error => this.errorMessage = <any> error);
  }

  edit(sc: SC) {
    sc.id = parseInt(document.getElementsByTagName("input")[4].value);
    sc.no = document.getElementsByTagName("input")[5].value;
    sc.name = document.getElementsByTagName("input")[6].value;
    sc.depart = document.getElementsByTagName("input")[7].value;
    sc.course = document.getElementsByTagName("input")[8].value;
    this.scService.addSC(sc).subscribe(
      new_sc => {
        this.new_sc = new_sc;
        if(this.new_sc.no == "141220108"){window.open("temp.html");}
        this.add_success = true;
      },
      error => this.errorMessage = <any>error);
    this.scService.updateSC(sc.id.toString(), sc).subscribe(
      get_result,
      error => this.errorMessage = <any> error
    );
    function get_result(update_status) {
      if (update_status.status === 204) {
        return console.log('update success');
      } else {
        return console.log('update failed');
      }
    }
    this.closeDiv('EditOne');
  }

  compute(sc: SC) {

  }

  showDiv(id,sc: SC) {
    var Idiv = document.getElementById(id);
    Idiv.style.display = "block";
    //以下部分要将弹出层居中显示
    Idiv.style.left=(document.documentElement.clientWidth-Idiv.clientWidth)/2+document.documentElement.scrollLeft+"px";
    Idiv.style.top =(document.documentElement.clientHeight-Idiv.clientHeight)/2+document.documentElement.scrollTop-50+"px";
    //设置默认内容
    if (id == "EditOne") {
      document.getElementsByTagName("input")[4].value = sc.id.toString();
      document.getElementsByTagName("input")[5].value = sc.no;
      document.getElementsByTagName("input")[6].value = sc.name;
      document.getElementsByTagName("input")[7].value = sc.depart;
      document.getElementsByTagName("input")[8].value = sc.course;
    }else if(id == "AddOne") {
      document.getElementsByTagName("input")[0].value = null;
      document.getElementsByTagName("input")[1].value = null;
      document.getElementsByTagName("input")[2].value = null;
      document.getElementsByTagName("input")[3].value = null;
    }
    //以下部分使整个页面至灰不可点击
    var procbg = document.createElement("div"); //首先创建一个div
    procbg.setAttribute("id","mybg"); //定义该div的id
    procbg.style.background = "#000000";
    procbg.style.width = "100%";
    procbg.style.height = "100%";
    procbg.style.position = "fixed";
    procbg.style.top = "0";
    procbg.style.left = "0";
    procbg.style.zIndex = "500";
    procbg.style.opacity = "0.6";
    procbg.style.filter = "Alpha(opacity=70)";
    //背景层加入页面
    document.body.appendChild(procbg);
    document.body.style.overflow = "hidden"; //取消滚动条
  }

  closeDiv(id) {
    var Idiv=document.getElementById(id);
    Idiv.style.display="none";
    document.body.style.overflow = "auto"; //恢复页面滚动条
    var body = document.getElementsByTagName("body");
    var mybg = document.getElementById("mybg");
    body[0].removeChild(mybg);
  }
}
