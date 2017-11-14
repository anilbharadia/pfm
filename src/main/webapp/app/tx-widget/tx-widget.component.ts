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
  styles: []
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
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  get TxTypes() {
    return TxTypes;
  }

  registerChangeInTransactions() {
    this.eventSubscriber = this.eventManager.subscribe(
      'transactionListModification', 
      (response) => this.loadTxs()
    );
  }

  loadTxs() {

    console.log('loadTxs() called')
    console.log('this.month = ', this.month);

    let dateFrom = new Date(this.month.getFullYear(), this.month.getMonth(), 1);
    console.log('dateFrom = ', dateFrom);

    let dateTo = new Date(this.month.getFullYear(), this.month.getMonth() + 1, 0);
    console.log('dateTo = ', dateTo);

    const filter = {
      txTypeIds: [this.txTypeId],
      dateFrom: dateFrom,
      dateTo: dateTo

    };

    this.txService.filter(filter).subscribe((response) => {
      this.transactions = response.json;
      this.totalAmount = this.transactions.length < 1 ? 0 : this.transactions.map((tx) => tx.amount).reduce((x, y) => x + y);
      this.amount.emit(this.totalAmount);
    });
  }

}
