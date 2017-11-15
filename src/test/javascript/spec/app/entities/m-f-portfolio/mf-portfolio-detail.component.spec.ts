/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MFPortfolioDetailComponent } from '../../../../../../main/webapp/app/entities/m-f-portfolio/mf-portfolio-detail.component';
import { MFPortfolioService } from '../../../../../../main/webapp/app/entities/m-f-portfolio/mf-portfolio.service';
import { MFPortfolio } from '../../../../../../main/webapp/app/entities/m-f-portfolio/mf-portfolio.model';

describe('Component Tests', () => {

    describe('MFPortfolio Management Detail Component', () => {
        let comp: MFPortfolioDetailComponent;
        let fixture: ComponentFixture<MFPortfolioDetailComponent>;
        let service: MFPortfolioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [MFPortfolioDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MFPortfolioService,
                    JhiEventManager
                ]
            }).overrideTemplate(MFPortfolioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MFPortfolioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MFPortfolioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MFPortfolio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mFPortfolio).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
