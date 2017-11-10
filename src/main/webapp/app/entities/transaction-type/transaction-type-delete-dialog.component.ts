import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TransactionType } from './transaction-type.model';
import { TransactionTypePopupService } from './transaction-type-popup.service';
import { TransactionTypeService } from './transaction-type.service';

@Component({
    selector: 'jhi-transaction-type-delete-dialog',
    templateUrl: './transaction-type-delete-dialog.component.html'
})
export class TransactionTypeDeleteDialogComponent {

    transactionType: TransactionType;

    constructor(
        private transactionTypeService: TransactionTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transactionTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'transactionTypeListModification',
                content: 'Deleted an transactionType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transaction-type-delete-popup',
    template: ''
})
export class TransactionTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionTypePopupService: TransactionTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.transactionTypePopupService
                .open(TransactionTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
