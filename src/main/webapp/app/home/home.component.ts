import { TxTypes } from './../entities/transaction-type/tx-type.enum';
import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    totalIncome: number;
    totalExpense: number;
    month = new Date();

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    get TxTypes() {
        return TxTypes;
    }

    previous() {
        console.log('previous() called')

        let date = this.month;
        this.month = new Date(date.getFullYear(), date.getMonth() - 1, date.getDay());

        console.log('this.month = ' + this.month);

        this.eventManager.broadcast({
            name: 'transactionListModification',
            content: 'Current Month Changed'
        });
    }
}
