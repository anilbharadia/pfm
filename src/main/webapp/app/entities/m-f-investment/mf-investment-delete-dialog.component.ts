import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MFInvestment } from './mf-investment.model';
import { MFInvestmentPopupService } from './mf-investment-popup.service';
import { MFInvestmentService } from './mf-investment.service';

@Component({
    selector: 'jhi-mf-investment-delete-dialog',
    templateUrl: './mf-investment-delete-dialog.component.html'
})
export class MFInvestmentDeleteDialogComponent {

    mFInvestment: MFInvestment;

    constructor(
        private mFInvestmentService: MFInvestmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mFInvestmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mFInvestmentListModification',
                content: 'Deleted an mFInvestment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mf-investment-delete-popup',
    template: ''
})
export class MFInvestmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFInvestmentPopupService: MFInvestmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mFInvestmentPopupService
                .open(MFInvestmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
