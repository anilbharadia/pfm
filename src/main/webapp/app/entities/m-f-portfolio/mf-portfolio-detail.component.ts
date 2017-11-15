import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MFPortfolio } from './mf-portfolio.model';
import { MFPortfolioService } from './mf-portfolio.service';

@Component({
    selector: 'jhi-mf-portfolio-detail',
    templateUrl: './mf-portfolio-detail.component.html'
})
export class MFPortfolioDetailComponent implements OnInit, OnDestroy {

    mFPortfolio: MFPortfolio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mFPortfolioService: MFPortfolioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMFPortfolios();
    }

    load(id) {
        this.mFPortfolioService.find(id).subscribe((mFPortfolio) => {
            this.mFPortfolio = mFPortfolio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMFPortfolios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mFPortfolioListModification',
            (response) => this.load(this.mFPortfolio.id)
        );
    }
}
