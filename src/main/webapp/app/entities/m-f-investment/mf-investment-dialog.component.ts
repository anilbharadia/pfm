import { MyAccountService } from './../my-account/my-account.service';
import { MyAccount } from './../my-account/my-account.model';
import { DatePipe } from '@angular/common';
import { AMCService } from './../a-mc/amc.service';
import { AMC } from './../a-mc/amc.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MFInvestment } from './mf-investment.model';
import { MFInvestmentPopupService } from './mf-investment-popup.service';
import { MFInvestmentService } from './mf-investment.service';
import { MutualFund, MutualFundService } from '../mutual-fund';
import { MFPortfolio, MFPortfolioService } from '../m-f-portfolio';
import { Goal, GoalService } from '../goal';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mf-investment-dialog',
    templateUrl: './mf-investment-dialog.component.html'
})
export class MFInvestmentDialogComponent implements OnInit {

    mFInvestment: MFInvestment;
    isSaving: boolean;

    mutualfunds: MutualFund[];

    amcs: AMC[];

    mfportfolios: MFPortfolio[];

    myaccounts: MyAccount[];

    goals: Goal[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mFInvestmentService: MFInvestmentService,
        private mutualFundService: MutualFundService,
        private mFPortfolioService: MFPortfolioService,
        private aMCService: AMCService,
        private myAccountService: MyAccountService,
        private goalService: GoalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.aMCService.query()
            .subscribe((res: ResponseWrapper) => { this.amcs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mutualFundService.query()
            .subscribe((res: ResponseWrapper) => { this.mutualfunds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mFPortfolioService.query()
            .subscribe((res: ResponseWrapper) => { this.mfportfolios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.myAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.myaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.goalService.query()
            .subscribe((res: ResponseWrapper) => { this.goals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mFInvestment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mFInvestmentService.update(this.mFInvestment));
        } else {
            this.subscribeToSaveResponse(
                this.mFInvestmentService.create(this.mFInvestment));
        }
    }

    private subscribeToSaveResponse(result: Observable<MFInvestment>) {
        result.subscribe((res: MFInvestment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MFInvestment) {
        this.eventManager.broadcast({ name: 'mFInvestmentListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMutualFundById(index: number, item: MutualFund) {
        return item.id;
    }

    trackMFPortfolioById(index: number, item: MFPortfolio) {
        return item.id;
    }

    trackGoalById(index: number, item: Goal) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mf-investment-popup',
    template: ''
})
export class MFInvestmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFInvestmentPopupService: MFInvestmentPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {

            let modal: Promise<NgbModalRef> = this.createDialogModal(params);

            modal.then((m) => {
                const mFInvestment = m.componentInstance.mFInvestment;

                this.setDataFromRouteParams(mFInvestment, params);

                this.setPurchaseDateToday(mFInvestment);
            });
        });
    }

    private setPurchaseDateToday(mFInvestment: any) {
        if (!mFInvestment.purchaseDate) {
            mFInvestment.purchaseDate = new DatePipe(navigator.language).transform(new Date(), 'yyyy-MM-ddThh:mm');
        }
    }

    private setDataFromRouteParams(mFInvestment: MFInvestment, params: { [key: string]: any; }) {
        if (params['goalId']) {
            mFInvestment.goalId = +params['goalId'];
        }
    }

    private createDialogModal(params: { [key: string]: any; }) {
        if (params['id']) {
            return this.mFInvestmentPopupService
                .open(MFInvestmentDialogComponent as Component, params['id']);
        }
        return this.mFInvestmentPopupService
            .open(MFInvestmentDialogComponent as Component);
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
