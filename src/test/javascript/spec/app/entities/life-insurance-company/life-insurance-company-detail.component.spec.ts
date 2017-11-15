/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LifeInsuranceCompanyDetailComponent } from '../../../../../../main/webapp/app/entities/life-insurance-company/life-insurance-company-detail.component';
import { LifeInsuranceCompanyService } from '../../../../../../main/webapp/app/entities/life-insurance-company/life-insurance-company.service';
import { LifeInsuranceCompany } from '../../../../../../main/webapp/app/entities/life-insurance-company/life-insurance-company.model';

describe('Component Tests', () => {

    describe('LifeInsuranceCompany Management Detail Component', () => {
        let comp: LifeInsuranceCompanyDetailComponent;
        let fixture: ComponentFixture<LifeInsuranceCompanyDetailComponent>;
        let service: LifeInsuranceCompanyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [LifeInsuranceCompanyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LifeInsuranceCompanyService,
                    JhiEventManager
                ]
            }).overrideTemplate(LifeInsuranceCompanyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LifeInsuranceCompanyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LifeInsuranceCompanyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LifeInsuranceCompany(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lifeInsuranceCompany).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
