import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericButtonComponent } from './generic-button.component';

describe('GenericButtonComponent', () => {
  let component: GenericButtonComponent;
  let fixture: ComponentFixture<GenericButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenericButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenericButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
