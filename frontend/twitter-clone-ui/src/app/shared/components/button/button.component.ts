import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DialogCreateTweetComponent} from "../dialog-create-tweet/dialog-create-tweet.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {

  @Input() text!: string;
  @Input() icon!: string;

  constructor(public dialog: MatDialog, private router: Router) {
  }

  ngOnInit(): void {
  }

  onClick() {
    switch (this.text) {
      case "Tweet":
        this.openDialog();
        break;
      case "More":
        //Open combobox
        console.log("More not implemented yet")
        break;
      default:
        this.goToUrl(this.text)
    }
  }

  goToUrl(url: String) {
    this.router.navigateByUrl('/' + url.toLowerCase())
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.width = "700" + "px";
    dialogConfig.height = "450" + "px";

    this.dialog.open(DialogCreateTweetComponent, dialogConfig);
  }
}
