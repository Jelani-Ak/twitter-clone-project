import { TestBed } from '@angular/core/testing';

import { RoleTestService } from './role-test.service';

describe('RoleTestService', () => {
  let service: RoleTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
