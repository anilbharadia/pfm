/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PPFTransactionDetailComponent } from '../../../../../../main/webapp/app/entities/p-pf-transaction/ppf-transaction-detail.component';
import { PPFTransactionService } from '../../../../../../main/webapp/app/entities/p-pf-transaction/ppf-transaction.service';
import { PPFTransaction } from '../../../../../../main/webapp/app/entities/p-pf-transaction/ppf-transaction.model';

describe('Component Tests', () => {

    describe('PPFTransaction Management Detail Component', () => {
        let comp: PPFTransactionDetailComponent;
        let fixture: ComponentFixture<PPFTransactionDetailComponent>;
        let service: PPFTransactionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [PPFTransactionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PPFTransactionService,
                    JhiEventManager
                ]
            }).overrideTemplate(PPFTransactionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PPFTransactionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PPFTransactionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PPFTransaction(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pPFTransaction).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
