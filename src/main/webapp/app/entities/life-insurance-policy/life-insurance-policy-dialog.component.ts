import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LifeInsurancePolicy } from './life-insurance-policy.model';
import { LifeInsurancePolicyPopupService } from './life-insurance-policy-popup.service';
import { LifeInsurancePolicyService } from './life-insurance-policy.service';
import { LifeInsuranceCompany, LifeInsuranceCompanyService } from '../life-insurance-company';
import { Person, PersonService } from '../person';
import { Goal, GoalService } from '../goal';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-life-insurance-policy-dialog',
    templateUrl: './life-insurance-policy-dialog.component.html'
})
export class LifeInsurancePolicyDialogComponent implements OnInit {

    lifeInsurancePolicy: LifeInsurancePolicy;
    isSaving: boolean;

    lifeinsurancecompanies: LifeInsuranceCompany[];

    people: Person[];

    goals: Goal[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lifeInsurancePolicyService: LifeInsurancePolicyService,
        private lifeInsuranceCompanyService: LifeInsuranceCompanyService,
        private personService: PersonService,
        private goalService: GoalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.lifeInsuranceCompanyService.query()
            .subscribe((res: ResponseWrapper) => { this.lifeinsurancecompanies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.goalService.query()
            .subscribe((res: ResponseWrapper) => { this.goals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lifeInsurancePolicy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lifeInsurancePolicyService.update(this.lifeInsurancePolicy));
        } else {
            this.subscribeToSaveResponse(
                this.lifeInsurancePolicyService.create(this.lifeInsurancePolicy));
        }
    }

    private subscribeToSaveResponse(result: Observable<LifeInsurancePolicy>) {
        result.subscribe((res: LifeInsurancePolicy) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LifeInsurancePolicy) {
        this.eventManager.broadcast({ name: 'lifeInsurancePolicyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLifeInsuranceCompanyById(index: number, item: LifeInsuranceCompany) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    trackGoalById(index: number, item: Goal) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-life-insurance-policy-popup',
    template: ''
})
export class LifeInsurancePolicyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lifeInsurancePolicyPopupService: LifeInsurancePolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lifeInsurancePolicyPopupService
                    .open(LifeInsurancePolicyDialogComponent as Component, params['id']);
            } else {
                this.lifeInsurancePolicyPopupService
                    .open(LifeInsurancePolicyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
