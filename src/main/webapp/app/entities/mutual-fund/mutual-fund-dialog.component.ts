import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MutualFund } from './mutual-fund.model';
import { MutualFundPopupService } from './mutual-fund-popup.service';
import { MutualFundService } from './mutual-fund.service';
import { AMC, AMCService } from '../a-mc';
import { MFCategory, MFCategoryService } from '../m-f-category';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mutual-fund-dialog',
    templateUrl: './mutual-fund-dialog.component.html'
})
export class MutualFundDialogComponent implements OnInit {

    mutualFund: MutualFund;
    isSaving: boolean;

    amcs: AMC[];

    mfcategories: MFCategory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mutualFundService: MutualFundService,
        private aMCService: AMCService,
        private mFCategoryService: MFCategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.aMCService.query()
            .subscribe((res: ResponseWrapper) => { this.amcs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mFCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.mfcategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mutualFund.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mutualFundService.update(this.mutualFund));
        } else {
            this.subscribeToSaveResponse(
                this.mutualFundService.create(this.mutualFund));
        }
    }

    private subscribeToSaveResponse(result: Observable<MutualFund>) {
        result.subscribe((res: MutualFund) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MutualFund) {
        this.eventManager.broadcast({ name: 'mutualFundListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAMCById(index: number, item: AMC) {
        return item.id;
    }

    trackMFCategoryById(index: number, item: MFCategory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mutual-fund-popup',
    template: ''
})
export class MutualFundPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mutualFundPopupService: MutualFundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mutualFundPopupService
                    .open(MutualFundDialogComponent as Component, params['id']);
            } else {
                this.mutualFundPopupService
                    .open(MutualFundDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
