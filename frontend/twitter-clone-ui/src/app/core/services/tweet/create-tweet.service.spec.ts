import { TestBed } from '@angular/core/testing';

import { CreateTweetService } from './create-tweet.service';

describe('CreateTweetService', () => {
  let service: CreateTweetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateTweetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
