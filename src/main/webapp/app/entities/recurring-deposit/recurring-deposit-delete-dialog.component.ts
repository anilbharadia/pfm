import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RecurringDeposit } from './recurring-deposit.model';
import { RecurringDepositPopupService } from './recurring-deposit-popup.service';
import { RecurringDepositService } from './recurring-deposit.service';

@Component({
    selector: 'jhi-recurring-deposit-delete-dialog',
    templateUrl: './recurring-deposit-delete-dialog.component.html'
})
export class RecurringDepositDeleteDialogComponent {

    recurringDeposit: RecurringDeposit;

    constructor(
        private recurringDepositService: RecurringDepositService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recurringDepositService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'recurringDepositListModification',
                content: 'Deleted an recurringDeposit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recurring-deposit-delete-popup',
    template: ''
})
export class RecurringDepositDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recurringDepositPopupService: RecurringDepositPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.recurringDepositPopupService
                .open(RecurringDepositDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
