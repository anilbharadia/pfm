import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LifeInsurancePolicy } from './life-insurance-policy.model';
import { LifeInsurancePolicyService } from './life-insurance-policy.service';

@Component({
    selector: 'jhi-life-insurance-policy-detail',
    templateUrl: './life-insurance-policy-detail.component.html'
})
export class LifeInsurancePolicyDetailComponent implements OnInit, OnDestroy {

    lifeInsurancePolicy: LifeInsurancePolicy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lifeInsurancePolicyService: LifeInsurancePolicyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLifeInsurancePolicies();
    }

    load(id) {
        this.lifeInsurancePolicyService.find(id).subscribe((lifeInsurancePolicy) => {
            this.lifeInsurancePolicy = lifeInsurancePolicy;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLifeInsurancePolicies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lifeInsurancePolicyListModification',
            (response) => this.load(this.lifeInsurancePolicy.id)
        );
    }
}
