import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { MFInvestment } from './mf-investment.model';
import { MFInvestmentService } from './mf-investment.service';

@Injectable()
export class MFInvestmentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private mFInvestmentService: MFInvestmentService

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
                this.mFInvestmentService.find(id).subscribe((mFInvestment) => {
                    mFInvestment.purchaseDate = this.datePipe
                        .transform(mFInvestment.purchaseDate, 'yyyy-MM-ddTHH:mm:ss');
                    mFInvestment.navDate = this.datePipe
                        .transform(mFInvestment.navDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.mFInvestmentModalRef(component, mFInvestment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mFInvestmentModalRef(component, new MFInvestment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mFInvestmentModalRef(component: Component, mFInvestment: MFInvestment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mFInvestment = mFInvestment;
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
