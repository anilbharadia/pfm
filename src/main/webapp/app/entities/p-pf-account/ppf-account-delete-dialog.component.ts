import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PPFAccount } from './ppf-account.model';
import { PPFAccountPopupService } from './ppf-account-popup.service';
import { PPFAccountService } from './ppf-account.service';

@Component({
    selector: 'jhi-ppf-account-delete-dialog',
    templateUrl: './ppf-account-delete-dialog.component.html'
})
export class PPFAccountDeleteDialogComponent {

    pPFAccount: PPFAccount;

    constructor(
        private pPFAccountService: PPFAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pPFAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pPFAccountListModification',
                content: 'Deleted an pPFAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ppf-account-delete-popup',
    template: ''
})
export class PPFAccountDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pPFAccountPopupService: PPFAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pPFAccountPopupService
                .open(PPFAccountDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
