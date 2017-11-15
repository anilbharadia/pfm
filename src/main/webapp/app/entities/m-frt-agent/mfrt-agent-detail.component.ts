import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MFRTAgent } from './mfrt-agent.model';
import { MFRTAgentService } from './mfrt-agent.service';

@Component({
    selector: 'jhi-mfrt-agent-detail',
    templateUrl: './mfrt-agent-detail.component.html'
})
export class MFRTAgentDetailComponent implements OnInit, OnDestroy {

    mFRTAgent: MFRTAgent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mFRTAgentService: MFRTAgentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMFRTAgents();
    }

    load(id) {
        this.mFRTAgentService.find(id).subscribe((mFRTAgent) => {
            this.mFRTAgent = mFRTAgent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMFRTAgents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mFRTAgentListModification',
            (response) => this.load(this.mFRTAgent.id)
        );
    }
}
