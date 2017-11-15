import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MFPortfolio } from './mf-portfolio.model';
import { MFPortfolioPopupService } from './mf-portfolio-popup.service';
import { MFPortfolioService } from './mf-portfolio.service';

@Component({
    selector: 'jhi-mf-portfolio-delete-dialog',
    templateUrl: './mf-portfolio-delete-dialog.component.html'
})
export class MFPortfolioDeleteDialogComponent {

    mFPortfolio: MFPortfolio;

    constructor(
        private mFPortfolioService: MFPortfolioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mFPortfolioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mFPortfolioListModification',
                content: 'Deleted an mFPortfolio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mf-portfolio-delete-popup',
    template: ''
})
export class MFPortfolioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFPortfolioPopupService: MFPortfolioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mFPortfolioPopupService
                .open(MFPortfolioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
