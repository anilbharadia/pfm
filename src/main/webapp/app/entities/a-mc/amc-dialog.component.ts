import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AMC } from './amc.model';
import { AMCPopupService } from './amc-popup.service';
import { AMCService } from './amc.service';
import { MFRTAgent, MFRTAgentService } from '../m-frt-agent';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-amc-dialog',
    templateUrl: './amc-dialog.component.html'
})
export class AMCDialogComponent implements OnInit {

    aMC: AMC;
    isSaving: boolean;

    mfrtagents: MFRTAgent[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private aMCService: AMCService,
        private mFRTAgentService: MFRTAgentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mFRTAgentService.query()
            .subscribe((res: ResponseWrapper) => { this.mfrtagents = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aMC.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aMCService.update(this.aMC));
        } else {
            this.subscribeToSaveResponse(
                this.aMCService.create(this.aMC));
        }
    }

    private subscribeToSaveResponse(result: Observable<AMC>) {
        result.subscribe((res: AMC) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AMC) {
        this.eventManager.broadcast({ name: 'aMCListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMFRTAgentById(index: number, item: MFRTAgent) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-amc-popup',
    template: ''
})
export class AMCPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aMCPopupService: AMCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.aMCPopupService
                    .open(AMCDialogComponent as Component, params['id']);
            } else {
                this.aMCPopupService
                    .open(AMCDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
