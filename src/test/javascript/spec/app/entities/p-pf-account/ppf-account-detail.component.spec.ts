/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfmTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PPFAccountDetailComponent } from '../../../../../../main/webapp/app/entities/p-pf-account/ppf-account-detail.component';
import { PPFAccountService } from '../../../../../../main/webapp/app/entities/p-pf-account/ppf-account.service';
import { PPFAccount } from '../../../../../../main/webapp/app/entities/p-pf-account/ppf-account.model';

describe('Component Tests', () => {

    describe('PPFAccount Management Detail Component', () => {
        let comp: PPFAccountDetailComponent;
        let fixture: ComponentFixture<PPFAccountDetailComponent>;
        let service: PPFAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfmTestModule],
                declarations: [PPFAccountDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PPFAccountService,
                    JhiEventManager
                ]
            }).overrideTemplate(PPFAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PPFAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PPFAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PPFAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pPFAccount).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
