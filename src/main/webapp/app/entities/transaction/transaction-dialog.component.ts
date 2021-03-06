import { DatePipe } from '@angular/common';
import { activateRoute } from './../../account/activate/activate.route';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable, Subscription } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Transaction } from './transaction.model';
import { TransactionPopupService } from './transaction-popup.service';
import { TransactionService } from './transaction.service';
import { MyAccount, MyAccountService } from '../my-account';
import { TransactionType, TransactionTypeService } from '../transaction-type';
import { ExpenseCategory, ExpenseCategoryService } from '../expense-category';
import { IncomeCategory, IncomeCategoryService } from '../income-category';
import { ResponseWrapper } from '../../shared';
import { TxTypes } from '../transaction-type/tx-type.enum';

@Component({
    selector: 'jhi-transaction-dialog',
    templateUrl: './transaction-dialog.component.html'
})
export class TransactionDialogComponent implements OnInit {

    transaction: Transaction;
    isSaving: boolean;

    myaccounts: MyAccount[];

    transactiontypes: TransactionType[];

    expensecategories: ExpenseCategory[];

    incomecategories: IncomeCategory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private transactionService: TransactionService,
        private myAccountService: MyAccountService,
        private transactionTypeService: TransactionTypeService,
        private expenseCategoryService: ExpenseCategoryService,
        private incomeCategoryService: IncomeCategoryService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {

        this.isSaving = false;
        this.myAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.myaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.transactionTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.transactiontypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.expenseCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.expensecategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.incomeCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.incomecategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.transaction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transactionService.update(this.transaction));
        } else {
            this.subscribeToSaveResponse(
                this.transactionService.create(this.transaction));
        }
    }

    private subscribeToSaveResponse(result: Observable<Transaction>) {
        result.subscribe((res: Transaction) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Transaction) {
        this.eventManager.broadcast({ name: 'transactionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMyAccountById(index: number, item: MyAccount) {
        return item.id;
    }

    trackTransactionTypeById(index: number, item: TransactionType) {
        return item.id;
    }

    trackExpenseCategoryById(index: number, item: ExpenseCategory) {
        return item.id;
    }

    trackIncomeCategoryById(index: number, item: IncomeCategory) {
        return item.id;
    }

    get TxTypes() {
        return TxTypes;
    }
}

@Component({
    selector: 'jhi-transaction-popup',
    template: ''
})
export class TransactionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionPopupService: TransactionPopupService
    ) {}

    ngOnInit() {

        this.routeSub = this.route.params.subscribe((params) => {

            let modal: Promise<NgbModalRef>;

            if ( params['id'] ) {
                modal = this.transactionPopupService
                    .open(TransactionDialogComponent as Component, params['id']);
            } else {
                modal = this.transactionPopupService
                    .open(TransactionDialogComponent as Component);
            }

            modal.then((m) => {

                const tx = m.componentInstance.transaction;

                if (params['txTypeId']) {
                    tx.txTypeId = +params['txTypeId'];
                }

                if (params['accountId']) {
                    tx.accountId = +params['accountId'];
                }

                if (!tx.date) {
                    tx.date = new DatePipe(navigator.language).transform(new Date(), 'yyyy-MM-ddThh:mm');
                }
            });
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
