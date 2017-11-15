import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Goal } from './goal.model';
import { GoalPopupService } from './goal-popup.service';
import { GoalService } from './goal.service';

@Component({
    selector: 'jhi-goal-dialog',
    templateUrl: './goal-dialog.component.html'
})
export class GoalDialogComponent implements OnInit {

    goal: Goal;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private goalService: GoalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.goal.id !== undefined) {
            this.subscribeToSaveResponse(
                this.goalService.update(this.goal));
        } else {
            this.subscribeToSaveResponse(
                this.goalService.create(this.goal));
        }
    }

    private subscribeToSaveResponse(result: Observable<Goal>) {
        result.subscribe((res: Goal) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Goal) {
        this.eventManager.broadcast({ name: 'goalListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-goal-popup',
    template: ''
})
export class GoalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private goalPopupService: GoalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.goalPopupService
                    .open(GoalDialogComponent as Component, params['id']);
            } else {
                this.goalPopupService
                    .open(GoalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
