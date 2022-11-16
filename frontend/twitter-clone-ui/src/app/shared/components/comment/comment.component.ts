import { Component, Input } from '@angular/core';
import { Tweet } from '../../models/tweet';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent {
  @Input() tweet!: Tweet;

  constructor() {}
}
