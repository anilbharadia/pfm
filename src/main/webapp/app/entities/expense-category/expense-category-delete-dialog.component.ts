import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExpenseCategory } from './expense-category.model';
import { ExpenseCategoryPopupService } from './expense-category-popup.service';
import { ExpenseCategoryService } from './expense-category.service';

@Component({
    selector: 'jhi-expense-category-delete-dialog',
    templateUrl: './expense-category-delete-dialog.component.html'
})
export class ExpenseCategoryDeleteDialogComponent {

    expenseCategory: ExpenseCategory;

    constructor(
        private expenseCategoryService: ExpenseCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.expenseCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'expenseCategoryListModification',
                content: 'Deleted an expenseCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-expense-category-delete-popup',
    template: ''
})
export class ExpenseCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private expenseCategoryPopupService: ExpenseCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.expenseCategoryPopupService
                .open(ExpenseCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
