/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MFRTAgentDetailComponent } from '../../../../../../main/webapp/app/entities/m-frt-agent/mfrt-agent-detail.component';
import { MFRTAgentService } from '../../../../../../main/webapp/app/entities/m-frt-agent/mfrt-agent.service';
import { MFRTAgent } from '../../../../../../main/webapp/app/entities/m-frt-agent/mfrt-agent.model';

describe('Component Tests', () => {

    describe('MFRTAgent Management Detail Component', () => {
        let comp: MFRTAgentDetailComponent;
        let fixture: ComponentFixture<MFRTAgentDetailComponent>;
        let service: MFRTAgentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [MFRTAgentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MFRTAgentService,
                    JhiEventManager
                ]
            }).overrideTemplate(MFRTAgentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MFRTAgentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MFRTAgentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MFRTAgent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mFRTAgent).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
