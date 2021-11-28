import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogCreateTweetComponent } from './dialog-create-tweet.component';

describe('DialogCreateTweetComponent', () => {
  let component: DialogCreateTweetComponent;
  let fixture: ComponentFixture<DialogCreateTweetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogCreateTweetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogCreateTweetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
