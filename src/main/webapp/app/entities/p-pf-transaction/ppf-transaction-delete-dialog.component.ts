import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PPFTransaction } from './ppf-transaction.model';
import { PPFTransactionPopupService } from './ppf-transaction-popup.service';
import { PPFTransactionService } from './ppf-transaction.service';

@Component({
    selector: 'jhi-ppf-transaction-delete-dialog',
    templateUrl: './ppf-transaction-delete-dialog.component.html'
})
export class PPFTransactionDeleteDialogComponent {

    pPFTransaction: PPFTransaction;

    constructor(
        private pPFTransactionService: PPFTransactionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pPFTransactionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pPFTransactionListModification',
                content: 'Deleted an pPFTransaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ppf-transaction-delete-popup',
    template: ''
})
export class PPFTransactionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pPFTransactionPopupService: PPFTransactionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pPFTransactionPopupService
                .open(PPFTransactionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
