import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TransactionCategory } from './transaction-category.model';
import { TransactionCategoryService } from './transaction-category.service';

@Component({
    selector: 'jhi-transaction-category-detail',
    templateUrl: './transaction-category-detail.component.html'
})
export class TransactionCategoryDetailComponent implements OnInit, OnDestroy {

    transactionCategory: TransactionCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private transactionCategoryService: TransactionCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTransactionCategories();
    }

    load(id) {
        this.transactionCategoryService.find(id).subscribe((transactionCategory) => {
            this.transactionCategory = transactionCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTransactionCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'transactionCategoryListModification',
            (response) => this.load(this.transactionCategory.id)
        );
    }
}
