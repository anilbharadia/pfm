import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IncomeCategory } from './income-category.model';
import { IncomeCategoryPopupService } from './income-category-popup.service';
import { IncomeCategoryService } from './income-category.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-income-category-dialog',
    templateUrl: './income-category-dialog.component.html'
})
export class IncomeCategoryDialogComponent implements OnInit {

    incomeCategory: IncomeCategory;
    isSaving: boolean;

    incomecategories: IncomeCategory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private incomeCategoryService: IncomeCategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.incomeCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.incomecategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.incomeCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.incomeCategoryService.update(this.incomeCategory));
        } else {
            this.subscribeToSaveResponse(
                this.incomeCategoryService.create(this.incomeCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<IncomeCategory>) {
        result.subscribe((res: IncomeCategory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: IncomeCategory) {
        this.eventManager.broadcast({ name: 'incomeCategoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIncomeCategoryById(index: number, item: IncomeCategory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-income-category-popup',
    template: ''
})
export class IncomeCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private incomeCategoryPopupService: IncomeCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.incomeCategoryPopupService
                    .open(IncomeCategoryDialogComponent as Component, params['id']);
            } else {
                this.incomeCategoryPopupService
                    .open(IncomeCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
