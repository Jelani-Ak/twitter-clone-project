import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainTweetComponent } from './main-tweet.component';

describe('MainTweetComponent', () => {
  let component: MainTweetComponent;
  let fixture: ComponentFixture<MainTweetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainTweetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainTweetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
