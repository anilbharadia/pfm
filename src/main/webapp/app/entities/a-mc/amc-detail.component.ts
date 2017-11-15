import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AMC } from './amc.model';
import { AMCService } from './amc.service';

@Component({
    selector: 'jhi-amc-detail',
    templateUrl: './amc-detail.component.html'
})
export class AMCDetailComponent implements OnInit, OnDestroy {

    aMC: AMC;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private aMCService: AMCService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAMCS();
    }

    load(id) {
        this.aMCService.find(id).subscribe((aMC) => {
            this.aMC = aMC;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAMCS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aMCListModification',
            (response) => this.load(this.aMC.id)
        );
    }
}
