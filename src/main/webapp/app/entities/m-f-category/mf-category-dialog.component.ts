import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MFCategory } from './mf-category.model';
import { MFCategoryPopupService } from './mf-category-popup.service';
import { MFCategoryService } from './mf-category.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mf-category-dialog',
    templateUrl: './mf-category-dialog.component.html'
})
export class MFCategoryDialogComponent implements OnInit {

    mFCategory: MFCategory;
    isSaving: boolean;

    mfcategories: MFCategory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mFCategoryService: MFCategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mFCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.mfcategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mFCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mFCategoryService.update(this.mFCategory));
        } else {
            this.subscribeToSaveResponse(
                this.mFCategoryService.create(this.mFCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<MFCategory>) {
        result.subscribe((res: MFCategory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MFCategory) {
        this.eventManager.broadcast({ name: 'mFCategoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMFCategoryById(index: number, item: MFCategory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mf-category-popup',
    template: ''
})
export class MFCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFCategoryPopupService: MFCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mFCategoryPopupService
                    .open(MFCategoryDialogComponent as Component, params['id']);
            } else {
                this.mFCategoryPopupService
                    .open(MFCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
