import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MFRTAgent } from './mfrt-agent.model';
import { MFRTAgentPopupService } from './mfrt-agent-popup.service';
import { MFRTAgentService } from './mfrt-agent.service';

@Component({
    selector: 'jhi-mfrt-agent-delete-dialog',
    templateUrl: './mfrt-agent-delete-dialog.component.html'
})
export class MFRTAgentDeleteDialogComponent {

    mFRTAgent: MFRTAgent;

    constructor(
        private mFRTAgentService: MFRTAgentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mFRTAgentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mFRTAgentListModification',
                content: 'Deleted an mFRTAgent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mfrt-agent-delete-popup',
    template: ''
})
export class MFRTAgentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFRTAgentPopupService: MFRTAgentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mFRTAgentPopupService
                .open(MFRTAgentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
