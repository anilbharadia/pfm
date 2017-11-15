import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LifeInsurancePolicy } from './life-insurance-policy.model';
import { LifeInsurancePolicyPopupService } from './life-insurance-policy-popup.service';
import { LifeInsurancePolicyService } from './life-insurance-policy.service';

@Component({
    selector: 'jhi-life-insurance-policy-delete-dialog',
    templateUrl: './life-insurance-policy-delete-dialog.component.html'
})
export class LifeInsurancePolicyDeleteDialogComponent {

    lifeInsurancePolicy: LifeInsurancePolicy;

    constructor(
        private lifeInsurancePolicyService: LifeInsurancePolicyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lifeInsurancePolicyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lifeInsurancePolicyListModification',
                content: 'Deleted an lifeInsurancePolicy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-life-insurance-policy-delete-popup',
    template: ''
})
export class LifeInsurancePolicyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lifeInsurancePolicyPopupService: LifeInsurancePolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lifeInsurancePolicyPopupService
                .open(LifeInsurancePolicyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
