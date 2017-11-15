import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RecurringDeposit } from './recurring-deposit.model';
import { RecurringDepositPopupService } from './recurring-deposit-popup.service';
import { RecurringDepositService } from './recurring-deposit.service';
import { Bank, BankService } from '../bank';
import { Goal, GoalService } from '../goal';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-recurring-deposit-dialog',
    templateUrl: './recurring-deposit-dialog.component.html'
})
export class RecurringDepositDialogComponent implements OnInit {

    recurringDeposit: RecurringDeposit;
    isSaving: boolean;

    banks: Bank[];

    goals: Goal[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private recurringDepositService: RecurringDepositService,
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
        if (this.recurringDeposit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.recurringDepositService.update(this.recurringDeposit));
        } else {
            this.subscribeToSaveResponse(
                this.recurringDepositService.create(this.recurringDeposit));
        }
    }

    private subscribeToSaveResponse(result: Observable<RecurringDeposit>) {
        result.subscribe((res: RecurringDeposit) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RecurringDeposit) {
        this.eventManager.broadcast({ name: 'recurringDepositListModification', content: 'OK'});
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
    selector: 'jhi-recurring-deposit-popup',
    template: ''
})
export class RecurringDepositPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recurringDepositPopupService: RecurringDepositPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.recurringDepositPopupService
                    .open(RecurringDepositDialogComponent as Component, params['id']);
            } else {
                this.recurringDepositPopupService
                    .open(RecurringDepositDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
