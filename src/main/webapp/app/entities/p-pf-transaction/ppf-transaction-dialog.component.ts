import { DatePipe } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PPFTransaction } from './ppf-transaction.model';
import { PPFTransactionPopupService } from './ppf-transaction-popup.service';
import { PPFTransactionService } from './ppf-transaction.service';
import { PPFAccount, PPFAccountService } from '../p-pf-account';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ppf-transaction-dialog',
    templateUrl: './ppf-transaction-dialog.component.html'
})
export class PPFTransactionDialogComponent implements OnInit {

    pPFTransaction: PPFTransaction;
    isSaving: boolean;

    ppfaccounts: PPFAccount[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pPFTransactionService: PPFTransactionService,
        private pPFAccountService: PPFAccountService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pPFAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.ppfaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        if (!this.pPFTransaction.date) {
            this.pPFTransaction.date = new DatePipe(navigator.language).transform(new Date(), 'yyyy-MM-ddThh:mm');
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pPFTransaction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pPFTransactionService.update(this.pPFTransaction));
        } else {
            this.subscribeToSaveResponse(
                this.pPFTransactionService.create(this.pPFTransaction));
        }
    }

    private subscribeToSaveResponse(result: Observable<PPFTransaction>) {
        result.subscribe((res: PPFTransaction) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PPFTransaction) {
        this.eventManager.broadcast({ name: 'pPFTransactionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPPFAccountById(index: number, item: PPFAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ppf-transaction-popup',
    template: ''
})
export class PPFTransactionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pPFTransactionPopupService: PPFTransactionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pPFTransactionPopupService
                    .open(PPFTransactionDialogComponent as Component, params['id']);
            } else {
                this.pPFTransactionPopupService
                    .open(PPFTransactionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
