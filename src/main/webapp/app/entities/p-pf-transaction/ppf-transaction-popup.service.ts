import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PPFTransaction } from './ppf-transaction.model';
import { PPFTransactionService } from './ppf-transaction.service';

@Injectable()
export class PPFTransactionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private pPFTransactionService: PPFTransactionService

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
                this.pPFTransactionService.find(id).subscribe((pPFTransaction) => {
                    pPFTransaction.date = this.datePipe
                        .transform(pPFTransaction.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.pPFTransactionModalRef(component, pPFTransaction);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pPFTransactionModalRef(component, new PPFTransaction());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pPFTransactionModalRef(component: Component, pPFTransaction: PPFTransaction): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pPFTransaction = pPFTransaction;
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
