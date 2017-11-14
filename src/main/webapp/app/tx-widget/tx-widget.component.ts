import { TxTypes } from './../entities/transaction-type/tx-type.enum';
import { TransactionType } from './../entities/transaction-type/transaction-type.model';
import { TransactionService } from './../entities/transaction/transaction.service';
import { Transaction } from './../entities/transaction/transaction.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'jhi-tx-widget',
  templateUrl: './tx-widget.component.html',
  styles: []
})
export class TxWidgetComponent implements OnInit {

  @Input('txType') txTypeId: TxTypes;

  transactions: Transaction[];
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(private txService: TransactionService) { }

  ngOnInit() {

    let filter = {
      txTypeIds: [this.txTypeId]
    };

    this.txService.filter(filter).subscribe((response) => {
      this.transactions = response.json;
    });
  }
  
  get TxTypes() {
    return TxTypes;
  }

}
