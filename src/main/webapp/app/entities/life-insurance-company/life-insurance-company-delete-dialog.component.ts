import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LifeInsuranceCompany } from './life-insurance-company.model';
import { LifeInsuranceCompanyPopupService } from './life-insurance-company-popup.service';
import { LifeInsuranceCompanyService } from './life-insurance-company.service';

@Component({
    selector: 'jhi-life-insurance-company-delete-dialog',
    templateUrl: './life-insurance-company-delete-dialog.component.html'
})
export class LifeInsuranceCompanyDeleteDialogComponent {

    lifeInsuranceCompany: LifeInsuranceCompany;

    constructor(
        private lifeInsuranceCompanyService: LifeInsuranceCompanyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lifeInsuranceCompanyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lifeInsuranceCompanyListModification',
                content: 'Deleted an lifeInsuranceCompany'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-life-insurance-company-delete-popup',
    template: ''
})
export class LifeInsuranceCompanyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lifeInsuranceCompanyPopupService: LifeInsuranceCompanyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lifeInsuranceCompanyPopupService
                .open(LifeInsuranceCompanyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
