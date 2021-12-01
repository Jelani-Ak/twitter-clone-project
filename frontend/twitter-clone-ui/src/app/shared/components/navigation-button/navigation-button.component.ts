import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DialogCreateTweetComponent} from "../../../core/components/dialog-create-tweet/dialog-create-tweet.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-button',
  templateUrl: './navigation-button.component.html',
  styleUrls: ['./navigation-button.component.css']
})
export class NavigationButtonComponent implements OnInit {

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
      case "Explore":
      case "Notifications":
      case "Messages":
      case "Bookmarks":
      case "Lists":
      case "Profile":
      case "More":
        //Open combobox
        console.log(this.text + " not implemented yet")
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
    dialogConfig.height = "298" + "px";

    this.dialog.open(DialogCreateTweetComponent, dialogConfig);
  }
}
