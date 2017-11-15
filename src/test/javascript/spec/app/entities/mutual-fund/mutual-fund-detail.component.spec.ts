/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MutualFundDetailComponent } from '../../../../../../main/webapp/app/entities/mutual-fund/mutual-fund-detail.component';
import { MutualFundService } from '../../../../../../main/webapp/app/entities/mutual-fund/mutual-fund.service';
import { MutualFund } from '../../../../../../main/webapp/app/entities/mutual-fund/mutual-fund.model';

describe('Component Tests', () => {

    describe('MutualFund Management Detail Component', () => {
        let comp: MutualFundDetailComponent;
        let fixture: ComponentFixture<MutualFundDetailComponent>;
        let service: MutualFundService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [MutualFundDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MutualFundService,
                    JhiEventManager
                ]
            }).overrideTemplate(MutualFundDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MutualFundDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MutualFundService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MutualFund(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mutualFund).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
