import { TransactionService } from './../entities/transaction/transaction.service';
import { Transaction } from './../entities/transaction/transaction.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-tx-widget',
  templateUrl: './tx-widget.component.html',
  styles: []
})
export class TxWidgetComponent implements OnInit {

  transactions: Transaction[];
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(private txService: TransactionService) { }

  ngOnInit() {
    this.txService.query({}).subscribe((response) => {
      this.transactions = response.json;
    });
  }

}
