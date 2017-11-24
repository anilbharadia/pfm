import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';
import { TxTypes } from './../entities/transaction-type/tx-type.enum';
import { TransactionType } from './../entities/transaction-type/transaction-type.model';
import { TransactionService } from './../entities/transaction/transaction.service';
import { Transaction } from './../entities/transaction/transaction.model';
import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';

@Component({
  selector: 'jhi-tx-widget',
  templateUrl: './tx-widget.component.html',
  styles: [`tr td{
    padding: 5px !important;
    margin: 5px !important;
  }`]
})
export class TxWidgetComponent implements OnInit, OnDestroy {

  @Input('txType') txTypeId: TxTypes;
  @Input() month: Date;
  // @Input() dateTo: Date;

  transactions: Transaction[];
  predicate: any;
  previousPage: any;
  reverse: any;
  totalAmount: number;
  @Output()
  amount: EventEmitter<number> = new EventEmitter<number>();
  eventSubscriber: Subscription;

  constructor(
    private txService: TransactionService,
    private eventManager: JhiEventManager
  ) { }

  ngOnInit() {

    this.loadTxs();

    this.registerChangeInTransactions();

    this.registerChangeInMFInvestment();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  get TxTypes() {
    return TxTypes;
  }

  registerChangeInMFInvestment() {
    this.eventSubscriber = this.eventManager.subscribe('mFInvestmentListModification', (response) => this.loadTxs());
  }

  registerChangeInTransactions() {
    this.eventSubscriber = this.eventManager.subscribe(
      'transactionListModification',
      (response) => this.loadTxs()
    );

    this.eventSubscriber = this.eventManager.subscribe(
      'currentMonthChanged',
      (event) => {
        console.log('currentMonthChanged event = ', event);
        this.month = event.month;
        this.loadTxs();
      }
    );
  }

  loadTxs() {

    const year = this.month.getFullYear();
    const month = this.month.getMonth();

    const dateFrom = new Date(year, month, 1, 0, 0, 0, 0);
    const dateTo = new Date(year, month + 1, 0, 23, 59, 59, 999);

    const filter = {
      txTypeIds: [this.txTypeId],
      dateFrom,
      dateTo
    };

    this.txService.filter(filter).subscribe((response) => {
      this.transactions = response.json;
      this.totalAmount = this.transactions.length < 1 ? 0 : this.transactions.map((tx) => tx.amount).reduce((x, y) => x + y);
      this.amount.emit(this.totalAmount);
    });
  }

}
