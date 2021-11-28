import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchNewsPanelComponent } from './search-news-panel.component';

describe('SearchNewsPanelComponent', () => {
  let component: SearchNewsPanelComponent;
  let fixture: ComponentFixture<SearchNewsPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchNewsPanelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchNewsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
