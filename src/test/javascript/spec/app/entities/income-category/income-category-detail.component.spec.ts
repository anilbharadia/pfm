/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IncomeCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/income-category/income-category-detail.component';
import { IncomeCategoryService } from '../../../../../../main/webapp/app/entities/income-category/income-category.service';
import { IncomeCategory } from '../../../../../../main/webapp/app/entities/income-category/income-category.model';

describe('Component Tests', () => {

    describe('IncomeCategory Management Detail Component', () => {
        let comp: IncomeCategoryDetailComponent;
        let fixture: ComponentFixture<IncomeCategoryDetailComponent>;
        let service: IncomeCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [IncomeCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IncomeCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(IncomeCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IncomeCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncomeCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IncomeCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.incomeCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
