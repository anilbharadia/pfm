/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RDTransactionDetailComponent } from '../../../../../../main/webapp/app/entities/r-d-transaction/rd-transaction-detail.component';
import { RDTransactionService } from '../../../../../../main/webapp/app/entities/r-d-transaction/rd-transaction.service';
import { RDTransaction } from '../../../../../../main/webapp/app/entities/r-d-transaction/rd-transaction.model';

describe('Component Tests', () => {

    describe('RDTransaction Management Detail Component', () => {
        let comp: RDTransactionDetailComponent;
        let fixture: ComponentFixture<RDTransactionDetailComponent>;
        let service: RDTransactionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [RDTransactionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RDTransactionService,
                    JhiEventManager
                ]
            }).overrideTemplate(RDTransactionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RDTransactionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RDTransactionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RDTransaction(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rDTransaction).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
