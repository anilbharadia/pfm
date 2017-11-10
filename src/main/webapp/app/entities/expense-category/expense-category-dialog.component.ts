import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExpenseCategory } from './expense-category.model';
import { ExpenseCategoryPopupService } from './expense-category-popup.service';
import { ExpenseCategoryService } from './expense-category.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-expense-category-dialog',
    templateUrl: './expense-category-dialog.component.html'
})
export class ExpenseCategoryDialogComponent implements OnInit {

    expenseCategory: ExpenseCategory;
    isSaving: boolean;

    expensecategories: ExpenseCategory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private expenseCategoryService: ExpenseCategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.expenseCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.expensecategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.expenseCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.expenseCategoryService.update(this.expenseCategory));
        } else {
            this.subscribeToSaveResponse(
                this.expenseCategoryService.create(this.expenseCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<ExpenseCategory>) {
        result.subscribe((res: ExpenseCategory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ExpenseCategory) {
        this.eventManager.broadcast({ name: 'expenseCategoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackExpenseCategoryById(index: number, item: ExpenseCategory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-expense-category-popup',
    template: ''
})
export class ExpenseCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private expenseCategoryPopupService: ExpenseCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.expenseCategoryPopupService
                    .open(ExpenseCategoryDialogComponent as Component, params['id']);
            } else {
                this.expenseCategoryPopupService
                    .open(ExpenseCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
