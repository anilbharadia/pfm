/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MFCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/m-f-category/mf-category-detail.component';
import { MFCategoryService } from '../../../../../../main/webapp/app/entities/m-f-category/mf-category.service';
import { MFCategory } from '../../../../../../main/webapp/app/entities/m-f-category/mf-category.model';

describe('Component Tests', () => {

    describe('MFCategory Management Detail Component', () => {
        let comp: MFCategoryDetailComponent;
        let fixture: ComponentFixture<MFCategoryDetailComponent>;
        let service: MFCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [MFCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MFCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(MFCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MFCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MFCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MFCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mFCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
