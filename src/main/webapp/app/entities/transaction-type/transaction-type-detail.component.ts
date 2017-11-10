import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TransactionType } from './transaction-type.model';
import { TransactionTypeService } from './transaction-type.service';

@Component({
    selector: 'jhi-transaction-type-detail',
    templateUrl: './transaction-type-detail.component.html'
})
export class TransactionTypeDetailComponent implements OnInit, OnDestroy {

    transactionType: TransactionType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private transactionTypeService: TransactionTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTransactionTypes();
    }

    load(id) {
        this.transactionTypeService.find(id).subscribe((transactionType) => {
            this.transactionType = transactionType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTransactionTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'transactionTypeListModification',
            (response) => this.load(this.transactionType.id)
        );
    }
}
