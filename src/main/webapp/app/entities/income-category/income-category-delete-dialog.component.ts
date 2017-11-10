import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IncomeCategory } from './income-category.model';
import { IncomeCategoryPopupService } from './income-category-popup.service';
import { IncomeCategoryService } from './income-category.service';

@Component({
    selector: 'jhi-income-category-delete-dialog',
    templateUrl: './income-category-delete-dialog.component.html'
})
export class IncomeCategoryDeleteDialogComponent {

    incomeCategory: IncomeCategory;

    constructor(
        private incomeCategoryService: IncomeCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incomeCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'incomeCategoryListModification',
                content: 'Deleted an incomeCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-income-category-delete-popup',
    template: ''
})
export class IncomeCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private incomeCategoryPopupService: IncomeCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.incomeCategoryPopupService
                .open(IncomeCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
