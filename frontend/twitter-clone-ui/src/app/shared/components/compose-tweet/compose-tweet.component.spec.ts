import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComposeTweetComponent } from './compose-tweet.component';

describe('ComposeTweetComponent', () => {
  let component: ComposeTweetComponent;
  let fixture: ComponentFixture<ComposeTweetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComposeTweetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComposeTweetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
