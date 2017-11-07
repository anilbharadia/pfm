import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TransactionCategory } from './transaction-category.model';
import { TransactionCategoryPopupService } from './transaction-category-popup.service';
import { TransactionCategoryService } from './transaction-category.service';

@Component({
    selector: 'jhi-transaction-category-dialog',
    templateUrl: './transaction-category-dialog.component.html'
})
export class TransactionCategoryDialogComponent implements OnInit {

    transactionCategory: TransactionCategory;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private transactionCategoryService: TransactionCategoryService,
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
        if (this.transactionCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transactionCategoryService.update(this.transactionCategory));
        } else {
            this.subscribeToSaveResponse(
                this.transactionCategoryService.create(this.transactionCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<TransactionCategory>) {
        result.subscribe((res: TransactionCategory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TransactionCategory) {
        this.eventManager.broadcast({ name: 'transactionCategoryListModification', content: 'OK'});
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
    selector: 'jhi-transaction-category-popup',
    template: ''
})
export class TransactionCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionCategoryPopupService: TransactionCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.transactionCategoryPopupService
                    .open(TransactionCategoryDialogComponent as Component, params['id']);
            } else {
                this.transactionCategoryPopupService
                    .open(TransactionCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
