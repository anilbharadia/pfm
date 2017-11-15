import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FixedDeposit } from './fixed-deposit.model';
import { FixedDepositService } from './fixed-deposit.service';

@Component({
    selector: 'jhi-fixed-deposit-detail',
    templateUrl: './fixed-deposit-detail.component.html'
})
export class FixedDepositDetailComponent implements OnInit, OnDestroy {

    fixedDeposit: FixedDeposit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fixedDepositService: FixedDepositService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFixedDeposits();
    }

    load(id) {
        this.fixedDepositService.find(id).subscribe((fixedDeposit) => {
            this.fixedDeposit = fixedDeposit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFixedDeposits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fixedDepositListModification',
            (response) => this.load(this.fixedDeposit.id)
        );
    }
}
