import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RDTransaction } from './rd-transaction.model';
import { RDTransactionPopupService } from './rd-transaction-popup.service';
import { RDTransactionService } from './rd-transaction.service';

@Component({
    selector: 'jhi-rd-transaction-delete-dialog',
    templateUrl: './rd-transaction-delete-dialog.component.html'
})
export class RDTransactionDeleteDialogComponent {

    rDTransaction: RDTransaction;

    constructor(
        private rDTransactionService: RDTransactionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rDTransactionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rDTransactionListModification',
                content: 'Deleted an rDTransaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rd-transaction-delete-popup',
    template: ''
})
export class RDTransactionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rDTransactionPopupService: RDTransactionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rDTransactionPopupService
                .open(RDTransactionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
