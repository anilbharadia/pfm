import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { IncomeCategory } from './income-category.model';
import { IncomeCategoryService } from './income-category.service';

@Component({
    selector: 'jhi-income-category-detail',
    templateUrl: './income-category-detail.component.html'
})
export class IncomeCategoryDetailComponent implements OnInit, OnDestroy {

    incomeCategory: IncomeCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private incomeCategoryService: IncomeCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIncomeCategories();
    }

    load(id) {
        this.incomeCategoryService.find(id).subscribe((incomeCategory) => {
            this.incomeCategory = incomeCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIncomeCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'incomeCategoryListModification',
            (response) => this.load(this.incomeCategory.id)
        );
    }
}
