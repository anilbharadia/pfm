import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ExpenseCategory } from './expense-category.model';
import { ExpenseCategoryService } from './expense-category.service';

@Component({
    selector: 'jhi-expense-category-detail',
    templateUrl: './expense-category-detail.component.html'
})
export class ExpenseCategoryDetailComponent implements OnInit, OnDestroy {

    expenseCategory: ExpenseCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private expenseCategoryService: ExpenseCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExpenseCategories();
    }

    load(id) {
        this.expenseCategoryService.find(id).subscribe((expenseCategory) => {
            this.expenseCategory = expenseCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExpenseCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'expenseCategoryListModification',
            (response) => this.load(this.expenseCategory.id)
        );
    }
}
