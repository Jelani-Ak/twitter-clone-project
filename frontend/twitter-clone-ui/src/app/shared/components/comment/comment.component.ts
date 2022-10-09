import { Component, Input, OnInit } from '@angular/core';
import { Tweet } from '../../models/tweet';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent implements OnInit {
  @Input() tweet!: Tweet;

  constructor() {}

  ngOnInit(): void {}
}
