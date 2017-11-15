import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MutualFund } from './mutual-fund.model';
import { MutualFundPopupService } from './mutual-fund-popup.service';
import { MutualFundService } from './mutual-fund.service';

@Component({
    selector: 'jhi-mutual-fund-delete-dialog',
    templateUrl: './mutual-fund-delete-dialog.component.html'
})
export class MutualFundDeleteDialogComponent {

    mutualFund: MutualFund;

    constructor(
        private mutualFundService: MutualFundService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mutualFundService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mutualFundListModification',
                content: 'Deleted an mutualFund'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mutual-fund-delete-popup',
    template: ''
})
export class MutualFundDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mutualFundPopupService: MutualFundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mutualFundPopupService
                .open(MutualFundDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
