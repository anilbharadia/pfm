import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MyAccount } from './my-account.model';
import { MyAccountPopupService } from './my-account-popup.service';
import { MyAccountService } from './my-account.service';
import { BankAccount, BankAccountService } from '../bank-account';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-my-account-dialog',
    templateUrl: './my-account-dialog.component.html'
})
export class MyAccountDialogComponent implements OnInit {

    myAccount: MyAccount;
    isSaving: boolean;

    bankaccounts: BankAccount[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private myAccountService: MyAccountService,
        private bankAccountService: BankAccountService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bankAccountService
            .query({filter: 'account-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.myAccount.bankAccountId) {
                    this.bankaccounts = res.json;
                } else {
                    this.bankAccountService
                        .find(this.myAccount.bankAccountId)
                        .subscribe((subRes: BankAccount) => {
                            this.bankaccounts = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.myAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.myAccountService.update(this.myAccount));
        } else {
            this.subscribeToSaveResponse(
                this.myAccountService.create(this.myAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<MyAccount>) {
        result.subscribe((res: MyAccount) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MyAccount) {
        this.eventManager.broadcast({ name: 'myAccountListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBankAccountById(index: number, item: BankAccount) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-my-account-popup',
    template: ''
})
export class MyAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private myAccountPopupService: MyAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.myAccountPopupService
                    .open(MyAccountDialogComponent as Component, params['id']);
            } else {
                this.myAccountPopupService
                    .open(MyAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
