import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RecurringDeposit } from './recurring-deposit.model';
import { RecurringDepositService } from './recurring-deposit.service';

@Component({
    selector: 'jhi-recurring-deposit-detail',
    templateUrl: './recurring-deposit-detail.component.html'
})
export class RecurringDepositDetailComponent implements OnInit, OnDestroy {

    recurringDeposit: RecurringDeposit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private recurringDepositService: RecurringDepositService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRecurringDeposits();
    }

    load(id) {
        this.recurringDepositService.find(id).subscribe((recurringDeposit) => {
            this.recurringDeposit = recurringDeposit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRecurringDeposits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'recurringDepositListModification',
            (response) => this.load(this.recurringDeposit.id)
        );
    }
}
