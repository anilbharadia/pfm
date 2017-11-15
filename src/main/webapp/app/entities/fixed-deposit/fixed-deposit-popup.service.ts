import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { FixedDeposit } from './fixed-deposit.model';
import { FixedDepositService } from './fixed-deposit.service';

@Injectable()
export class FixedDepositPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private fixedDepositService: FixedDepositService

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
                this.fixedDepositService.find(id).subscribe((fixedDeposit) => {
                    fixedDeposit.openDate = this.datePipe
                        .transform(fixedDeposit.openDate, 'yyyy-MM-ddTHH:mm:ss');
                    fixedDeposit.maturityDate = this.datePipe
                        .transform(fixedDeposit.maturityDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.fixedDepositModalRef(component, fixedDeposit);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.fixedDepositModalRef(component, new FixedDeposit());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    fixedDepositModalRef(component: Component, fixedDeposit: FixedDeposit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fixedDeposit = fixedDeposit;
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
