import { Subscription } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { ResponseWrapper } from './../shared/model/response-wrapper.model';
import { Goal } from './../entities/goal/goal.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { GoalService } from '../entities/goal/goal.service';

@Component({
  selector: 'jhi-goals-widget',
  templateUrl: './goals-widget.component.html',
  styles: []
})
export class GoalsWidgetComponent implements OnInit, OnDestroy {

  goals: Goal[];
  eventSubscriber: Subscription;

  constructor(
    private jhiAlertService: JhiAlertService,
    private goalService: GoalService,
    private eventManager: JhiEventManager
  ) { }

  loadAll() {
    this.goalService.query({}).subscribe(
      (res: ResponseWrapper) => this.goals = res.json,
      (res: ResponseWrapper) => this.jhiAlertService.error(res.json.message, null, null)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGoals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  registerChangeInGoals() {
    this.eventSubscriber = this.eventManager.subscribe('goalListModification', (response) => this.loadAll());
  }

}
