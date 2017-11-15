import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PPFAccount } from './ppf-account.model';
import { PPFAccountPopupService } from './ppf-account-popup.service';
import { PPFAccountService } from './ppf-account.service';
import { Bank, BankService } from '../bank';
import { Person, PersonService } from '../person';
import { Goal, GoalService } from '../goal';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ppf-account-dialog',
    templateUrl: './ppf-account-dialog.component.html'
})
export class PPFAccountDialogComponent implements OnInit {

    pPFAccount: PPFAccount;
    isSaving: boolean;

    banks: Bank[];

    people: Person[];

    goals: Goal[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pPFAccountService: PPFAccountService,
        private bankService: BankService,
        private personService: PersonService,
        private goalService: GoalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bankService.query()
            .subscribe((res: ResponseWrapper) => { this.banks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.goalService.query()
            .subscribe((res: ResponseWrapper) => { this.goals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pPFAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pPFAccountService.update(this.pPFAccount));
        } else {
            this.subscribeToSaveResponse(
                this.pPFAccountService.create(this.pPFAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<PPFAccount>) {
        result.subscribe((res: PPFAccount) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PPFAccount) {
        this.eventManager.broadcast({ name: 'pPFAccountListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    trackGoalById(index: number, item: Goal) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ppf-account-popup',
    template: ''
})
export class PPFAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pPFAccountPopupService: PPFAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pPFAccountPopupService
                    .open(PPFAccountDialogComponent as Component, params['id']);
            } else {
                this.pPFAccountPopupService
                    .open(PPFAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
