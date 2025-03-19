import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyRegisterComponent } from './property-register.component';

describe('PropertyRegisterComponent', () => {
  let component: PropertyRegisterComponent;
  let fixture: ComponentFixture<PropertyRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropertyRegisterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
