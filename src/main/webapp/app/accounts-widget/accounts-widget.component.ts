import { Subscription } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { ResponseWrapper } from './../shared/model/response-wrapper.model';
import { MyAccountService } from './../entities/my-account/my-account.service';
import { MyAccount } from './../entities/my-account/my-account.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { TxTypes } from '../entities/transaction-type/tx-type.enum';

@Component({
  selector: 'jhi-accounts-widget',
  templateUrl: './accounts-widget.component.html',
  styles: []
})
export class AccountsWidgetComponent implements OnInit, OnDestroy {

  accounts: MyAccount[];
  eventSubscriber: Subscription;

  constructor(
    private accountService: MyAccountService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager
  ) { }

  ngOnInit() {
    this.load();
    this.registerChangeInTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  load() {
    this.accountService.query().subscribe(
      (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
      (res: ResponseWrapper) => this.onError(res.json)
    );
  }

  registerChangeInTransactions() {
    this.eventSubscriber = this.eventManager.subscribe(
      'transactionListModification',
      (response) => this.load()
    );
  }

  private onSuccess(data, headers) {
    this.accounts = data;
  }
  private onError(error) {
      this.jhiAlertService.error(error.message, null, null);
  }

}
