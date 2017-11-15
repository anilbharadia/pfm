import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LifeInsuranceCompany } from './life-insurance-company.model';
import { LifeInsuranceCompanyPopupService } from './life-insurance-company-popup.service';
import { LifeInsuranceCompanyService } from './life-insurance-company.service';

@Component({
    selector: 'jhi-life-insurance-company-dialog',
    templateUrl: './life-insurance-company-dialog.component.html'
})
export class LifeInsuranceCompanyDialogComponent implements OnInit {

    lifeInsuranceCompany: LifeInsuranceCompany;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lifeInsuranceCompanyService: LifeInsuranceCompanyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lifeInsuranceCompany.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lifeInsuranceCompanyService.update(this.lifeInsuranceCompany));
        } else {
            this.subscribeToSaveResponse(
                this.lifeInsuranceCompanyService.create(this.lifeInsuranceCompany));
        }
    }

    private subscribeToSaveResponse(result: Observable<LifeInsuranceCompany>) {
        result.subscribe((res: LifeInsuranceCompany) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LifeInsuranceCompany) {
        this.eventManager.broadcast({ name: 'lifeInsuranceCompanyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-life-insurance-company-popup',
    template: ''
})
export class LifeInsuranceCompanyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lifeInsuranceCompanyPopupService: LifeInsuranceCompanyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lifeInsuranceCompanyPopupService
                    .open(LifeInsuranceCompanyDialogComponent as Component, params['id']);
            } else {
                this.lifeInsuranceCompanyPopupService
                    .open(LifeInsuranceCompanyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
