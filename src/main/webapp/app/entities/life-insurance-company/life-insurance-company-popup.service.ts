import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LifeInsuranceCompany } from './life-insurance-company.model';
import { LifeInsuranceCompanyService } from './life-insurance-company.service';

@Injectable()
export class LifeInsuranceCompanyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private lifeInsuranceCompanyService: LifeInsuranceCompanyService

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
                this.lifeInsuranceCompanyService.find(id).subscribe((lifeInsuranceCompany) => {
                    this.ngbModalRef = this.lifeInsuranceCompanyModalRef(component, lifeInsuranceCompany);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lifeInsuranceCompanyModalRef(component, new LifeInsuranceCompany());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lifeInsuranceCompanyModalRef(component: Component, lifeInsuranceCompany: LifeInsuranceCompany): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lifeInsuranceCompany = lifeInsuranceCompany;
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
