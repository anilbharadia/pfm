import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LifeInsurancePolicy } from './life-insurance-policy.model';
import { LifeInsurancePolicyService } from './life-insurance-policy.service';

@Injectable()
export class LifeInsurancePolicyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private lifeInsurancePolicyService: LifeInsurancePolicyService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.lifeInsurancePolicyService.find(id).subscribe((lifeInsurancePolicy) => {
                    this.ngbModalRef = this.lifeInsurancePolicyModalRef(component, lifeInsurancePolicy);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lifeInsurancePolicyModalRef(component, new LifeInsurancePolicy());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lifeInsurancePolicyModalRef(component: Component, lifeInsurancePolicy: LifeInsurancePolicy): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lifeInsurancePolicy = lifeInsurancePolicy;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
