import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MFCategory } from './mf-category.model';
import { MFCategoryPopupService } from './mf-category-popup.service';
import { MFCategoryService } from './mf-category.service';

@Component({
    selector: 'jhi-mf-category-delete-dialog',
    templateUrl: './mf-category-delete-dialog.component.html'
})
export class MFCategoryDeleteDialogComponent {

    mFCategory: MFCategory;

    constructor(
        private mFCategoryService: MFCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mFCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mFCategoryListModification',
                content: 'Deleted an mFCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mf-category-delete-popup',
    template: ''
})
export class MFCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mFCategoryPopupService: MFCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mFCategoryPopupService
                .open(MFCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
