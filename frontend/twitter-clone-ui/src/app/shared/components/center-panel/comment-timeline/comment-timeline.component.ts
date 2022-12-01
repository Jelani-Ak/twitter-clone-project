import { Component, Input } from '@angular/core';
import { Comment } from 'src/app/shared/models/tweet';

@Component({
  selector: 'app-comment-timeline',
  templateUrl: './comment-timeline.component.html',
  styleUrls: ['./comment-timeline.component.css'],
})
export class CommentTimelineComponent {
  @Input() public comments: Comment[] = [];

  public deleteComment($event: any) {
    this.comments.forEach((comment) => {
      if (comment == $event.comment) this.comments.splice($event.index, 1);
    });
  }
}
