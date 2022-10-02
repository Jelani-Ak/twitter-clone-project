import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TweetManagementButtonsComponent } from './tweet-management-buttons.component';

describe('TweetManagementButtonsComponent', () => {
  let component: TweetManagementButtonsComponent;
  let fixture: ComponentFixture<TweetManagementButtonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TweetManagementButtonsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TweetManagementButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
