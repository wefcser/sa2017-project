import {Component, OnInit, Input} from '@angular/core';
import {SCService} from './sc.service';
import {SC} from './sc';
import {Page} from './page';
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
  pages: Page[];
  @Input() page: Page;
  response_status: number;
  add_success: boolean = false;
  delete_success: boolean = false;

  pageNo = '1';
  import_url = this.scService.getImportUrl();

  constructor(private router: Router, private scService: SCService) {
  }

  ngOnInit() {
    this.scService.getPage().subscribe(
      pages => this.pages = pages,
      error => this.errorMessage = <any> error);
    this.scService.getPartSCs(this.pageNo).subscribe(
      scs => this.scs = scs,
      error => this.errorMessage = <any> error);
  }
  pageChange(page: Page){
    this.scService.getPartSCs(page.no.toString()).subscribe(
      scs => this.scs = scs,
      error => this.errorMessage = <any> error);
    this.pageNo = page.no.toString();
  }
  addOne(sc: SC) {
    sc.id = null;
    sc.no = document.getElementsByTagName("input")[0].value;
    sc.name = document.getElementsByTagName("input")[1].value;
    sc.depart = document.getElementsByTagName("input")[2].value;
    sc.course = document.getElementsByTagName("input")[3].value;
    sc.grade = null;
    this.scService.addSC(sc).subscribe(
      new_sc => {
        this.new_sc = new_sc;
        this.add_success = true;
        this.scService.getPage().subscribe(
          pages => {
            this.pages = pages;
            if(parseInt(this.pageNo)<this.pages.length) {
              this.pageNo = this.pages.length.toString();
              window.open("app.component.html");
            }
            this.scService.getPartSCs(this.pageNo).subscribe(
              scs => this.scs = scs,
              error => this.errorMessage = <any> error);
          },
          error => this.errorMessage = <any> error);
      },
      error => this.errorMessage = <any>error);
    this.closeDiv('AddOne');
  }

  importFile() {
    var fileObj = document.getElementsByTagName("input")[14].value;
    //if(fileObj=="")return;
    var FileController = "http://localhost:8080/scis/api/import";
    // FormData 对象
    //var form = new FormData();
    //var form = new FormData(document.getElementsByTagName("form")[0]);

    // 增加表单数据
    //form.append("file", fileObj);                           // 文件对象

    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    // xhr.open("post", FileController, false);
    // xhr.setRequestHeader("content-type","multipart/form-data");
    // xhr.onload = function () {
    //   alert("上传完成!");
    // };
    // xhr.upload.addEventListener("progress", this.progressFunction, false);
    // xhr.send(form);
    xhr.open("get", FileController, false);
    xhr.send();
    this.scService.getPage().subscribe(
      pages => {
        this.pages = pages;
        if(parseInt(this.pageNo)<this.pages.length) {
          this.pageNo = this.pages.length.toString();
          window.open("app.component.html");
        }
        this.scService.getPartSCs(this.pageNo).subscribe(
          scs => this.scs = scs,
          error => this.errorMessage = <any> error);
      },
      error => this.errorMessage = <any> error);
    this.closeDiv("Import");
  }

  progressFunction(evt) {
    window.open("app.component.html");
    var progressBar = document.getElementsByTagName("progress")[0];
    var percentageDiv = document.getElementsByTagName("span")[0];
    if (evt.lengthComputable) {
      progressBar.max = evt.total;
      progressBar.value = evt.loaded;
      percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
    }
  }



  delete(sc: SC) {
    if(sc.id == null)return;
    this.scService.deleteSC(sc.id.toString()).subscribe(
      response => {
        this.response_status = response;
        if (this.response_status === 204) {
          this.delete_success = true;
          this.sc = <SC>{};
        }
        this.scService.getPage().subscribe(
          pages => {
            this.pages = pages;
            if(parseInt(this.pageNo)>this.pages.length) {
              this.pageNo = this.pages.length.toString();
              window.open("app.component.html");
            }
            this.scService.getPartSCs(this.pageNo).subscribe(
              scs => this.scs = scs,
              error => this.errorMessage = <any> error
            );
          },
          error => this.errorMessage = <any> error
          );
      },
      error => this.errorMessage = <any> error);
  }

  edit(sc: SC) {
    var id:number =parseInt(document.getElementsByTagName("input")[4].value);
    for(var i=0,n=this.scs.length;i<n;i++){
      if(this.scs[i].id == id){
        sc=this.scs[i];
        break;
      }
    }
    sc.no = document.getElementsByTagName("input")[5].value;
    sc.name = document.getElementsByTagName("input")[6].value;
    sc.depart = document.getElementsByTagName("input")[7].value;
    sc.course = document.getElementsByTagName("input")[8].value;
    var grade = parseInt(document.getElementsByTagName("input")[9].value);
    if(!isNaN(grade)) sc.grade = grade;
    this.scService.updateSC(sc.id.toString(), sc).subscribe(
      update_status => get_result,
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

  grade(sc: SC) {
    var id:number =parseInt(document.getElementsByTagName("input")[4].value);
    for(var i=0,n=this.scs.length;i<n;i++){
      if(this.scs[i].id == id){
        sc=this.scs[i];
        break;
      }
    }
    sc.no = document.getElementsByTagName("input")[5].value;
    sc.name = document.getElementsByTagName("input")[6].value;
    sc.depart = document.getElementsByTagName("input")[7].value;
    sc.course = document.getElementsByTagName("input")[8].value;
    var grade1:number = parseInt(document.getElementsByTagName("input")[10].value);
    var grade2:number = parseInt(document.getElementsByTagName("input")[11].value);
    var grade3:number = parseInt(document.getElementsByTagName("input")[12].value);
    var grade4:number = Math.round(grade1*0.1+grade2*0.3+grade3*0.6);
    if(!isNaN(grade1)&&!isNaN(grade2)&&!isNaN(grade3)) sc.grade = grade4;
    this.scService.updateSC(sc.id.toString(), sc).subscribe(
      update_status => get_result,
      error => this.errorMessage = <any> error
    );
    function get_result(update_status) {
      if (update_status.status === 204) {
        return console.log('update success');
      } else {
        return console.log('update failed');
      }
    }
    this.closeDiv('Grade');
  }

  showDiv(id,sc: SC) {
    if((id == "EditOne"||id == "Grade")&&sc.id == null)return;
    var Idiv = document.getElementById(id);
    Idiv.style.display = "block";
    //以下部分要将弹出层居中显示
    Idiv.style.left=(document.documentElement.clientWidth-Idiv.clientWidth)/2+document.documentElement.scrollLeft+"px";
    Idiv.style.top =(document.documentElement.clientHeight-Idiv.clientHeight)/2+document.documentElement.scrollTop-50+"px";
    //设置默认内容//用于传参
    if (id == "EditOne") {
      document.getElementsByTagName("input")[4].value = "";
      document.getElementsByTagName("input")[5].value = "";
      document.getElementsByTagName("input")[6].value = "";
      document.getElementsByTagName("input")[7].value = "";
      document.getElementsByTagName("input")[8].value = "";
      document.getElementsByTagName("input")[9].value = "";
      document.getElementsByTagName("input")[4].value = sc.id.toString();
      document.getElementsByTagName("input")[5].value = sc.no;
      document.getElementsByTagName("input")[6].value = sc.name;
      document.getElementsByTagName("input")[7].value = sc.depart;
      document.getElementsByTagName("input")[8].value = sc.course;
      if(sc.grade != null)document.getElementsByTagName("input")[9].value = sc.grade.toString();
    }else if(id == "AddOne") {
      document.getElementsByTagName("input")[0].value = null;
      document.getElementsByTagName("input")[1].value = null;
      document.getElementsByTagName("input")[2].value = null;
      document.getElementsByTagName("input")[3].value = null;
    }else if(id == "Grade") {
      document.getElementsByTagName("input")[4].value = "";
      document.getElementsByTagName("input")[5].value = "";
      document.getElementsByTagName("input")[6].value = "";
      document.getElementsByTagName("input")[7].value = "";
      document.getElementsByTagName("input")[8].value = "";
      document.getElementsByTagName("input")[9].value = "";
      document.getElementsByTagName("input")[10].value = "";
      document.getElementsByTagName("input")[11].value = "";
      document.getElementsByTagName("input")[12].value = "";
      document.getElementsByTagName("input")[4].value = sc.id.toString();
      document.getElementsByTagName("input")[5].value = sc.no;
      document.getElementsByTagName("input")[6].value = sc.name;
      document.getElementsByTagName("input")[7].value = sc.depart;
      document.getElementsByTagName("input")[8].value = sc.course;
      if(sc.grade != null)document.getElementsByTagName("input")[9].value = sc.grade.toString();
    }else if(id == 'Import'){
      document.getElementsByTagName("input")[14].value = "";
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
