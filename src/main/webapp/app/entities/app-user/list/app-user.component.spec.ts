import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AppUserService } from '../service/app-user.service';

import { AppUserComponent } from './app-user.component';
import SpyInstance = jest.SpyInstance;

describe('AppUser Management Component', () => {
  let comp: AppUserComponent;
  let fixture: ComponentFixture<AppUserComponent>;
  let service: AppUserService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'app-user', component: AppUserComponent }]), HttpClientTestingModule],
      declarations: [AppUserComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'idUser,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'idUser,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AppUserComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AppUserComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AppUserService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ idUser: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.appUsers?.[0]).toEqual(expect.objectContaining({ idUser: 123 }));
  });

  describe('trackIdUser', () => {
    it('Should forward to appUserService', () => {
      const entity = { idUser: 123 };
      jest.spyOn(service, 'getAppUserIdentifier');
      const idUser = comp.trackIdUser(0, entity);
      expect(service.getAppUserIdentifier).toHaveBeenCalledWith(entity);
      expect(idUser).toBe(entity.idUser);
    });
  });

  it('should load a page', () => {
    // WHEN
    comp.navigateToPage(1);

    // THEN
    expect(routerNavigateSpy).toHaveBeenCalled();
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['idUser,desc'] }));
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // GIVEN
    comp.predicate = 'name';

    // WHEN
    comp.navigateToWithComponentValues();

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['name,asc'],
        }),
      })
    );
  });

  it('should re-initialize the page', () => {
    // WHEN
    comp.loadPage(1);
    comp.reset();

    // THEN
    expect(comp.page).toEqual(1);
    expect(service.query).toHaveBeenCalledTimes(2);
    expect(comp.appUsers?.[0]).toEqual(expect.objectContaining({ idUser: 123 }));
  });
});
