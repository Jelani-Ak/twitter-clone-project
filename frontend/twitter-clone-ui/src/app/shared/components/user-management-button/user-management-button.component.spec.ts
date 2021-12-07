import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManagementButtonComponent } from './user-management-button.component';

describe('UserManagementButtonComponent', () => {
  let component: UserManagementButtonComponent;
  let fixture: ComponentFixture<UserManagementButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserManagementButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserManagementButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
