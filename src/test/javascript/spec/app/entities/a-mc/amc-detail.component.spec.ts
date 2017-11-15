/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AMCDetailComponent } from '../../../../../../main/webapp/app/entities/a-mc/amc-detail.component';
import { AMCService } from '../../../../../../main/webapp/app/entities/a-mc/amc.service';
import { AMC } from '../../../../../../main/webapp/app/entities/a-mc/amc.model';

describe('Component Tests', () => {

    describe('AMC Management Detail Component', () => {
        let comp: AMCDetailComponent;
        let fixture: ComponentFixture<AMCDetailComponent>;
        let service: AMCService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [AMCDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AMCService,
                    JhiEventManager
                ]
            }).overrideTemplate(AMCDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AMCDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AMCService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AMC(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.aMC).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
