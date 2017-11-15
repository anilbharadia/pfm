/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LifeInsurancePolicyDetailComponent } from '../../../../../../main/webapp/app/entities/life-insurance-policy/life-insurance-policy-detail.component';
import { LifeInsurancePolicyService } from '../../../../../../main/webapp/app/entities/life-insurance-policy/life-insurance-policy.service';
import { LifeInsurancePolicy } from '../../../../../../main/webapp/app/entities/life-insurance-policy/life-insurance-policy.model';

describe('Component Tests', () => {

    describe('LifeInsurancePolicy Management Detail Component', () => {
        let comp: LifeInsurancePolicyDetailComponent;
        let fixture: ComponentFixture<LifeInsurancePolicyDetailComponent>;
        let service: LifeInsurancePolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [LifeInsurancePolicyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LifeInsurancePolicyService,
                    JhiEventManager
                ]
            }).overrideTemplate(LifeInsurancePolicyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LifeInsurancePolicyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LifeInsurancePolicyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LifeInsurancePolicy(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lifeInsurancePolicy).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
