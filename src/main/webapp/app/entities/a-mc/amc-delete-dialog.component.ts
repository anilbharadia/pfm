import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AMC } from './amc.model';
import { AMCPopupService } from './amc-popup.service';
import { AMCService } from './amc.service';

@Component({
    selector: 'jhi-amc-delete-dialog',
    templateUrl: './amc-delete-dialog.component.html'
})
export class AMCDeleteDialogComponent {

    aMC: AMC;

    constructor(
        private aMCService: AMCService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aMCService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aMCListModification',
                content: 'Deleted an aMC'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-amc-delete-popup',
    template: ''
})
export class AMCDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aMCPopupService: AMCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.aMCPopupService
                .open(AMCDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
