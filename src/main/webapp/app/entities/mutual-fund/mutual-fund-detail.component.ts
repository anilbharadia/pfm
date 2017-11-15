import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MutualFund } from './mutual-fund.model';
import { MutualFundService } from './mutual-fund.service';

@Component({
    selector: 'jhi-mutual-fund-detail',
    templateUrl: './mutual-fund-detail.component.html'
})
export class MutualFundDetailComponent implements OnInit, OnDestroy {

    mutualFund: MutualFund;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mutualFundService: MutualFundService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMutualFunds();
    }

    load(id) {
        this.mutualFundService.find(id).subscribe((mutualFund) => {
            this.mutualFund = mutualFund;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMutualFunds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mutualFundListModification',
            (response) => this.load(this.mutualFund.id)
        );
    }
}
