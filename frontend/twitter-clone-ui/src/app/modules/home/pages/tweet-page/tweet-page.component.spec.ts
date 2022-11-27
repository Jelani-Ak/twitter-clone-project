import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TweetPageComponent } from './tweet-page.component';

describe('TweetComponent', () => {
  let component: TweetPageComponent;
  let fixture: ComponentFixture<TweetPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TweetPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TweetPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
