import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentTimelineComponent } from './comment-timeline.component';

describe('CommentTimelineComponent', () => {
  let component: CommentTimelineComponent;
  let fixture: ComponentFixture<CommentTimelineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentTimelineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
