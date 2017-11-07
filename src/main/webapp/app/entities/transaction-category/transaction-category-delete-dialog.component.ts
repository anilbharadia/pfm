import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TransactionCategory } from './transaction-category.model';
import { TransactionCategoryPopupService } from './transaction-category-popup.service';
import { TransactionCategoryService } from './transaction-category.service';

@Component({
    selector: 'jhi-transaction-category-delete-dialog',
    templateUrl: './transaction-category-delete-dialog.component.html'
})
export class TransactionCategoryDeleteDialogComponent {

    transactionCategory: TransactionCategory;

    constructor(
        private transactionCategoryService: TransactionCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transactionCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'transactionCategoryListModification',
                content: 'Deleted an transactionCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transaction-category-delete-popup',
    template: ''
})
export class TransactionCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionCategoryPopupService: TransactionCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.transactionCategoryPopupService
                .open(TransactionCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
