import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-dialog-create-tweet',
  templateUrl: './dialog-create-tweet.component.html',
  styleUrls: ['./dialog-create-tweet.component.css']
})
export class DialogCreateTweetComponent implements OnInit {

  saveTweetDetails: FormGroup;
  content: FormControl = new FormControl('');

  constructor() {
    this.saveTweetDetails = new FormGroup({
      content: this.content
    })
  }

  ngOnInit(): void {
  }

}
