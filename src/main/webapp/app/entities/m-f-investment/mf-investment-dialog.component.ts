import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
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

    mfportfolios: MFPortfolio[];

    goals: Goal[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mFInvestmentService: MFInvestmentService,
        private mutualFundService: MutualFundService,
        private mFPortfolioService: MFPortfolioService,
        private goalService: GoalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mutualFundService.query()
            .subscribe((res: ResponseWrapper) => { this.mutualfunds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mFPortfolioService.query()
            .subscribe((res: ResponseWrapper) => { this.mfportfolios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        this.eventManager.broadcast({ name: 'mFInvestmentListModification', content: 'OK'});
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
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mFInvestmentPopupService
                    .open(MFInvestmentDialogComponent as Component, params['id']);
            } else {
                this.mFInvestmentPopupService
                    .open(MFInvestmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
