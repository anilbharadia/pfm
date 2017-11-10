import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ExpenseCategory } from './expense-category.model';
import { ExpenseCategoryService } from './expense-category.service';

@Injectable()
export class ExpenseCategoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private expenseCategoryService: ExpenseCategoryService

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
                this.expenseCategoryService.find(id).subscribe((expenseCategory) => {
                    this.ngbModalRef = this.expenseCategoryModalRef(component, expenseCategory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.expenseCategoryModalRef(component, new ExpenseCategory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    expenseCategoryModalRef(component: Component, expenseCategory: ExpenseCategory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.expenseCategory = expenseCategory;
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
