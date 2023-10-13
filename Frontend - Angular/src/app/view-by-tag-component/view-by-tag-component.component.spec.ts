import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewByTagComponentComponent } from './view-by-tag-component.component';

describe('ViewByTagComponentComponent', () => {
  let component: ViewByTagComponentComponent;
  let fixture: ComponentFixture<ViewByTagComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewByTagComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewByTagComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
