import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TransactionType } from './transaction-type.model';
import { TransactionTypePopupService } from './transaction-type-popup.service';
import { TransactionTypeService } from './transaction-type.service';

@Component({
    selector: 'jhi-transaction-type-dialog',
    templateUrl: './transaction-type-dialog.component.html'
})
export class TransactionTypeDialogComponent implements OnInit {

    transactionType: TransactionType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private transactionTypeService: TransactionTypeService,
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
        if (this.transactionType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transactionTypeService.update(this.transactionType));
        } else {
            this.subscribeToSaveResponse(
                this.transactionTypeService.create(this.transactionType));
        }
    }

    private subscribeToSaveResponse(result: Observable<TransactionType>) {
        result.subscribe((res: TransactionType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TransactionType) {
        this.eventManager.broadcast({ name: 'transactionTypeListModification', content: 'OK'});
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
    selector: 'jhi-transaction-type-popup',
    template: ''
})
export class TransactionTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionTypePopupService: TransactionTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.transactionTypePopupService
                    .open(TransactionTypeDialogComponent as Component, params['id']);
            } else {
                this.transactionTypePopupService
                    .open(TransactionTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
