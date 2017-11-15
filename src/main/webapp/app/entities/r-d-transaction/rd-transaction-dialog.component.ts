import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RDTransaction } from './rd-transaction.model';
import { RDTransactionPopupService } from './rd-transaction-popup.service';
import { RDTransactionService } from './rd-transaction.service';
import { RecurringDeposit, RecurringDepositService } from '../recurring-deposit';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rd-transaction-dialog',
    templateUrl: './rd-transaction-dialog.component.html'
})
export class RDTransactionDialogComponent implements OnInit {

    rDTransaction: RDTransaction;
    isSaving: boolean;

    recurringdeposits: RecurringDeposit[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rDTransactionService: RDTransactionService,
        private recurringDepositService: RecurringDepositService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.recurringDepositService.query()
            .subscribe((res: ResponseWrapper) => { this.recurringdeposits = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rDTransaction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rDTransactionService.update(this.rDTransaction));
        } else {
            this.subscribeToSaveResponse(
                this.rDTransactionService.create(this.rDTransaction));
        }
    }

    private subscribeToSaveResponse(result: Observable<RDTransaction>) {
        result.subscribe((res: RDTransaction) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RDTransaction) {
        this.eventManager.broadcast({ name: 'rDTransactionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRecurringDepositById(index: number, item: RecurringDeposit) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rd-transaction-popup',
    template: ''
})
export class RDTransactionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rDTransactionPopupService: RDTransactionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rDTransactionPopupService
                    .open(RDTransactionDialogComponent as Component, params['id']);
            } else {
                this.rDTransactionPopupService
                    .open(RDTransactionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
