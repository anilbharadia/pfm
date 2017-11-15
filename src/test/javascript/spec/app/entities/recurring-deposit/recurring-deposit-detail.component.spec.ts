/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RecurringDepositDetailComponent } from '../../../../../../main/webapp/app/entities/recurring-deposit/recurring-deposit-detail.component';
import { RecurringDepositService } from '../../../../../../main/webapp/app/entities/recurring-deposit/recurring-deposit.service';
import { RecurringDeposit } from '../../../../../../main/webapp/app/entities/recurring-deposit/recurring-deposit.model';

describe('Component Tests', () => {

    describe('RecurringDeposit Management Detail Component', () => {
        let comp: RecurringDepositDetailComponent;
        let fixture: ComponentFixture<RecurringDepositDetailComponent>;
        let service: RecurringDepositService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [RecurringDepositDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RecurringDepositService,
                    JhiEventManager
                ]
            }).overrideTemplate(RecurringDepositDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecurringDepositDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecurringDepositService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RecurringDeposit(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.recurringDeposit).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
