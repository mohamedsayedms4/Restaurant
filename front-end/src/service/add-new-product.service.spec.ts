import { TestBed } from '@angular/core/testing';

import { AddNewProductService } from './add-new-product.service';

describe('AddNewProductService', () => {
  let service: AddNewProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddNewProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
