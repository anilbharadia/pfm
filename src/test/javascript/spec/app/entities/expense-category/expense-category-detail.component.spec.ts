/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExpenseCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/expense-category/expense-category-detail.component';
import { ExpenseCategoryService } from '../../../../../../main/webapp/app/entities/expense-category/expense-category.service';
import { ExpenseCategory } from '../../../../../../main/webapp/app/entities/expense-category/expense-category.model';

describe('Component Tests', () => {

    describe('ExpenseCategory Management Detail Component', () => {
        let comp: ExpenseCategoryDetailComponent;
        let fixture: ComponentFixture<ExpenseCategoryDetailComponent>;
        let service: ExpenseCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [ExpenseCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExpenseCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExpenseCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExpenseCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExpenseCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ExpenseCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.expenseCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
