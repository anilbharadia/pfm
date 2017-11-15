import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FixedDeposit } from './fixed-deposit.model';
import { FixedDepositPopupService } from './fixed-deposit-popup.service';
import { FixedDepositService } from './fixed-deposit.service';

@Component({
    selector: 'jhi-fixed-deposit-delete-dialog',
    templateUrl: './fixed-deposit-delete-dialog.component.html'
})
export class FixedDepositDeleteDialogComponent {

    fixedDeposit: FixedDeposit;

    constructor(
        private fixedDepositService: FixedDepositService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fixedDepositService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fixedDepositListModification',
                content: 'Deleted an fixedDeposit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fixed-deposit-delete-popup',
    template: ''
})
export class FixedDepositDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fixedDepositPopupService: FixedDepositPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fixedDepositPopupService
                .open(FixedDepositDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
