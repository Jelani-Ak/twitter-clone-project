import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DialogBoxComponent} from "../dialog-box/dialog-box.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation-button',
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

  navigate() {
    switch (this.text) {
      case "Tweet":
        this.openDialog();
        break;
      case "Explore":
      case "Notifications":
      case "Messages":
      //Direct messages
      case "Bookmarks":
      //Tweets that have been saved
      case "Lists":

      case "Profile":
      //Allow the user to change details/settings
      case "More":
        //Open combobox with extra features
        console.log(this.text + " not implemented yet")
        break;
      default:
        this.goToUrl(this.text)
        break;
    }
  }

  goToUrl(url: String) {
    this.router.navigateByUrl('/' + url.toLowerCase())
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.width = "700" + "px";

    this.dialog.open(DialogBoxComponent, dialogConfig);
  }
}
