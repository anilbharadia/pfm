import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MFRTAgent } from './mfrt-agent.model';
import { MFRTAgentPopupService } from './mfrt-agent-popup.service';
import { MFRTAgentService } from './mfrt-agent.service';

@Component({
    selector: 'jhi-mfrt-agent-dialog',
    templateUrl: './mfrt-agent-dialog.component.html'
})
export class MFRTAgentDialogComponent implements OnInit {

    mFRTAgent: MFRTAgent;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mFRTAgentService: MFRTAgentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mFRTAgent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mFRTAgentService.update(this.mFRTAgent));
        } else {
            this.subscribeToSaveResponse(
                this.mFRTAgentService.create(this.mFRTAgent));
        }
    }

    private subscribeToSaveResponse(result: Observable<MFRTAgent>) {
        result.subscribe((res: MFRTAgent) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MFRTAgent) {
        this.eventManager.broadcast({ name: 'mFRTAgentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-mfrt-agent-popup',
    template: ''
})
export class MFRTAgentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFRTAgentPopupService: MFRTAgentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mFRTAgentPopupService
                    .open(MFRTAgentDialogComponent as Component, params['id']);
            } else {
                this.mFRTAgentPopupService
                    .open(MFRTAgentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
