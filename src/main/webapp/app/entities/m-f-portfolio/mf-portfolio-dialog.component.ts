import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MFPortfolio } from './mf-portfolio.model';
import { MFPortfolioPopupService } from './mf-portfolio-popup.service';
import { MFPortfolioService } from './mf-portfolio.service';
import { AMC, AMCService } from '../a-mc';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mf-portfolio-dialog',
    templateUrl: './mf-portfolio-dialog.component.html'
})
export class MFPortfolioDialogComponent implements OnInit {

    mFPortfolio: MFPortfolio;
    isSaving: boolean;

    amcs: AMC[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mFPortfolioService: MFPortfolioService,
        private aMCService: AMCService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.aMCService.query()
            .subscribe((res: ResponseWrapper) => { this.amcs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mFPortfolio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mFPortfolioService.update(this.mFPortfolio));
        } else {
            this.subscribeToSaveResponse(
                this.mFPortfolioService.create(this.mFPortfolio));
        }
    }

    private subscribeToSaveResponse(result: Observable<MFPortfolio>) {
        result.subscribe((res: MFPortfolio) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MFPortfolio) {
        this.eventManager.broadcast({ name: 'mFPortfolioListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mf-portfolio-popup',
    template: ''
})
export class MFPortfolioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFPortfolioPopupService: MFPortfolioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mFPortfolioPopupService
                    .open(MFPortfolioDialogComponent as Component, params['id']);
            } else {
                this.mFPortfolioPopupService
                    .open(MFPortfolioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
