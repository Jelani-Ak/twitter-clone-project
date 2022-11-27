import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComposeCommentComponent } from './compose-comment.component';

describe('ComposeCommentComponent', () => {
  let component: ComposeCommentComponent;
  let fixture: ComponentFixture<ComposeCommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComposeCommentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComposeCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
