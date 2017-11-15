import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RecurringDeposit } from './recurring-deposit.model';
import { RecurringDepositService } from './recurring-deposit.service';

@Injectable()
export class RecurringDepositPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private recurringDepositService: RecurringDepositService

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
                this.recurringDepositService.find(id).subscribe((recurringDeposit) => {
                    recurringDeposit.startDate = this.datePipe
                        .transform(recurringDeposit.startDate, 'yyyy-MM-ddTHH:mm:ss');
                    recurringDeposit.endDate = this.datePipe
                        .transform(recurringDeposit.endDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.recurringDepositModalRef(component, recurringDeposit);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.recurringDepositModalRef(component, new RecurringDeposit());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    recurringDepositModalRef(component: Component, recurringDeposit: RecurringDeposit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.recurringDeposit = recurringDeposit;
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
