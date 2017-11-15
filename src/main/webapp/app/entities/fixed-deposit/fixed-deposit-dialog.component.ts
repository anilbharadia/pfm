import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FixedDeposit } from './fixed-deposit.model';
import { FixedDepositPopupService } from './fixed-deposit-popup.service';
import { FixedDepositService } from './fixed-deposit.service';
import { Bank, BankService } from '../bank';
import { Goal, GoalService } from '../goal';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-fixed-deposit-dialog',
    templateUrl: './fixed-deposit-dialog.component.html'
})
export class FixedDepositDialogComponent implements OnInit {

    fixedDeposit: FixedDeposit;
    isSaving: boolean;

    banks: Bank[];

    goals: Goal[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private fixedDepositService: FixedDepositService,
        private bankService: BankService,
        private goalService: GoalService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bankService.query()
            .subscribe((res: ResponseWrapper) => { this.banks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.goalService.query()
            .subscribe((res: ResponseWrapper) => { this.goals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fixedDeposit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fixedDepositService.update(this.fixedDeposit));
        } else {
            this.subscribeToSaveResponse(
                this.fixedDepositService.create(this.fixedDeposit));
        }
    }

    private subscribeToSaveResponse(result: Observable<FixedDeposit>) {
        result.subscribe((res: FixedDeposit) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FixedDeposit) {
        this.eventManager.broadcast({ name: 'fixedDepositListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBankById(index: number, item: Bank) {
        return item.id;
    }

    trackGoalById(index: number, item: Goal) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-fixed-deposit-popup',
    template: ''
})
export class FixedDepositPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fixedDepositPopupService: FixedDepositPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fixedDepositPopupService
                    .open(FixedDepositDialogComponent as Component, params['id']);
            } else {
                this.fixedDepositPopupService
                    .open(FixedDepositDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
