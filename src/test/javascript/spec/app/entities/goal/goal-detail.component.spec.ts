/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GoalDetailComponent } from '../../../../../../main/webapp/app/entities/goal/goal-detail.component';
import { GoalService } from '../../../../../../main/webapp/app/entities/goal/goal.service';
import { Goal } from '../../../../../../main/webapp/app/entities/goal/goal.model';

describe('Component Tests', () => {

    describe('Goal Management Detail Component', () => {
        let comp: GoalDetailComponent;
        let fixture: ComponentFixture<GoalDetailComponent>;
        let service: GoalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [GoalDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GoalService,
                    JhiEventManager
                ]
            }).overrideTemplate(GoalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Goal(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.goal).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
