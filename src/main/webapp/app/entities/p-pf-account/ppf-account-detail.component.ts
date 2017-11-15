import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PPFAccount } from './ppf-account.model';
import { PPFAccountService } from './ppf-account.service';

@Component({
    selector: 'jhi-ppf-account-detail',
    templateUrl: './ppf-account-detail.component.html'
})
export class PPFAccountDetailComponent implements OnInit, OnDestroy {

    pPFAccount: PPFAccount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pPFAccountService: PPFAccountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPPFAccounts();
    }

    load(id) {
        this.pPFAccountService.find(id).subscribe((pPFAccount) => {
            this.pPFAccount = pPFAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPPFAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pPFAccountListModification',
            (response) => this.load(this.pPFAccount.id)
        );
    }
}
