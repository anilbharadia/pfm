/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransactionCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/transaction-category/transaction-category-detail.component';
import { TransactionCategoryService } from '../../../../../../main/webapp/app/entities/transaction-category/transaction-category.service';
import { TransactionCategory } from '../../../../../../main/webapp/app/entities/transaction-category/transaction-category.model';

describe('Component Tests', () => {

    describe('TransactionCategory Management Detail Component', () => {
        let comp: TransactionCategoryDetailComponent;
        let fixture: ComponentFixture<TransactionCategoryDetailComponent>;
        let service: TransactionCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [TransactionCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TransactionCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(TransactionCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransactionCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransactionCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transactionCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
