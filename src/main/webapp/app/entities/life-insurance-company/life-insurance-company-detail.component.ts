import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LifeInsuranceCompany } from './life-insurance-company.model';
import { LifeInsuranceCompanyService } from './life-insurance-company.service';

@Component({
    selector: 'jhi-life-insurance-company-detail',
    templateUrl: './life-insurance-company-detail.component.html'
})
export class LifeInsuranceCompanyDetailComponent implements OnInit, OnDestroy {

    lifeInsuranceCompany: LifeInsuranceCompany;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lifeInsuranceCompanyService: LifeInsuranceCompanyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLifeInsuranceCompanies();
    }

    load(id) {
        this.lifeInsuranceCompanyService.find(id).subscribe((lifeInsuranceCompany) => {
            this.lifeInsuranceCompany = lifeInsuranceCompany;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLifeInsuranceCompanies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lifeInsuranceCompanyListModification',
            (response) => this.load(this.lifeInsuranceCompany.id)
        );
    }
}
