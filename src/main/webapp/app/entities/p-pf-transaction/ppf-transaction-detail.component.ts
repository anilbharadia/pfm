import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PPFTransaction } from './ppf-transaction.model';
import { PPFTransactionService } from './ppf-transaction.service';

@Component({
    selector: 'jhi-ppf-transaction-detail',
    templateUrl: './ppf-transaction-detail.component.html'
})
export class PPFTransactionDetailComponent implements OnInit, OnDestroy {

    pPFTransaction: PPFTransaction;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pPFTransactionService: PPFTransactionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPPFTransactions();
    }

    load(id) {
        this.pPFTransactionService.find(id).subscribe((pPFTransaction) => {
            this.pPFTransaction = pPFTransaction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPPFTransactions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pPFTransactionListModification',
            (response) => this.load(this.pPFTransaction.id)
        );
    }
}
