import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TagSideBarComponent } from './tag-side-bar.component';

describe('TagSideBarComponent', () => {
  let component: TagSideBarComponent;
  let fixture: ComponentFixture<TagSideBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TagSideBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TagSideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
