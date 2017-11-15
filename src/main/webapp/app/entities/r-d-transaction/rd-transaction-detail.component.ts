import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RDTransaction } from './rd-transaction.model';
import { RDTransactionService } from './rd-transaction.service';

@Component({
    selector: 'jhi-rd-transaction-detail',
    templateUrl: './rd-transaction-detail.component.html'
})
export class RDTransactionDetailComponent implements OnInit, OnDestroy {

    rDTransaction: RDTransaction;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rDTransactionService: RDTransactionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRDTransactions();
    }

    load(id) {
        this.rDTransactionService.find(id).subscribe((rDTransaction) => {
            this.rDTransaction = rDTransaction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRDTransactions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rDTransactionListModification',
            (response) => this.load(this.rDTransaction.id)
        );
    }
}
