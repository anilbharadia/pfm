/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FixedDepositDetailComponent } from '../../../../../../main/webapp/app/entities/fixed-deposit/fixed-deposit-detail.component';
import { FixedDepositService } from '../../../../../../main/webapp/app/entities/fixed-deposit/fixed-deposit.service';
import { FixedDeposit } from '../../../../../../main/webapp/app/entities/fixed-deposit/fixed-deposit.model';

describe('Component Tests', () => {

    describe('FixedDeposit Management Detail Component', () => {
        let comp: FixedDepositDetailComponent;
        let fixture: ComponentFixture<FixedDepositDetailComponent>;
        let service: FixedDepositService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [FixedDepositDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FixedDepositService,
                    JhiEventManager
                ]
            }).overrideTemplate(FixedDepositDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FixedDepositDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FixedDepositService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FixedDeposit(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fixedDeposit).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
