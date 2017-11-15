import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MFInvestment } from './mf-investment.model';
import { MFInvestmentService } from './mf-investment.service';

@Component({
    selector: 'jhi-mf-investment-detail',
    templateUrl: './mf-investment-detail.component.html'
})
export class MFInvestmentDetailComponent implements OnInit, OnDestroy {

    mFInvestment: MFInvestment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mFInvestmentService: MFInvestmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMFInvestments();
    }

    load(id) {
        this.mFInvestmentService.find(id).subscribe((mFInvestment) => {
            this.mFInvestment = mFInvestment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMFInvestments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mFInvestmentListModification',
            (response) => this.load(this.mFInvestment.id)
        );
    }
}
