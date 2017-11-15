import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MFCategory } from './mf-category.model';
import { MFCategoryService } from './mf-category.service';

@Component({
    selector: 'jhi-mf-category-detail',
    templateUrl: './mf-category-detail.component.html'
})
export class MFCategoryDetailComponent implements OnInit, OnDestroy {

    mFCategory: MFCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mFCategoryService: MFCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMFCategories();
    }

    load(id) {
        this.mFCategoryService.find(id).subscribe((mFCategory) => {
            this.mFCategory = mFCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMFCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mFCategoryListModification',
            (response) => this.load(this.mFCategory.id)
        );
    }
}
