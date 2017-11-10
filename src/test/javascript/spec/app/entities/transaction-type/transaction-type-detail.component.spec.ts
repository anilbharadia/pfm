/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransactionTypeDetailComponent } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type-detail.component';
import { TransactionTypeService } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.service';
import { TransactionType } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.model';

describe('Component Tests', () => {

    describe('TransactionType Management Detail Component', () => {
        let comp: TransactionTypeDetailComponent;
        let fixture: ComponentFixture<TransactionTypeDetailComponent>;
        let service: TransactionTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [TransactionTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TransactionTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(TransactionTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransactionTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransactionType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transactionType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
