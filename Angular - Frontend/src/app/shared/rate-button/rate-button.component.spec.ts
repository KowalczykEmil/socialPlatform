import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateButtonComponent } from './rate-button.component';

describe('RateButtonComponent', () => {
  let component: RateButtonComponent;
  let fixture: ComponentFixture<RateButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
