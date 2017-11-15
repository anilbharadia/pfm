import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RDTransaction } from './rd-transaction.model';
import { RDTransactionService } from './rd-transaction.service';

@Injectable()
export class RDTransactionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rDTransactionService: RDTransactionService

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
                this.rDTransactionService.find(id).subscribe((rDTransaction) => {
                    rDTransaction.date = this.datePipe
                        .transform(rDTransaction.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rDTransactionModalRef(component, rDTransaction);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rDTransactionModalRef(component, new RDTransaction());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rDTransactionModalRef(component: Component, rDTransaction: RDTransaction): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rDTransaction = rDTransaction;
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
