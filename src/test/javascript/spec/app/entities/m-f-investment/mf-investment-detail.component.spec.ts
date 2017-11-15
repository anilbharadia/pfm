/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MFInvestmentDetailComponent } from '../../../../../../main/webapp/app/entities/m-f-investment/mf-investment-detail.component';
import { MFInvestmentService } from '../../../../../../main/webapp/app/entities/m-f-investment/mf-investment.service';
import { MFInvestment } from '../../../../../../main/webapp/app/entities/m-f-investment/mf-investment.model';

describe('Component Tests', () => {

    describe('MFInvestment Management Detail Component', () => {
        let comp: MFInvestmentDetailComponent;
        let fixture: ComponentFixture<MFInvestmentDetailComponent>;
        let service: MFInvestmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [MFInvestmentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MFInvestmentService,
                    JhiEventManager
                ]
            }).overrideTemplate(MFInvestmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MFInvestmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MFInvestmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MFInvestment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mFInvestment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
