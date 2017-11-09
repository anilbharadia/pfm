import { MyAccount } from './../entities/my-account/my-account.model';
import { MyAccountService } from './../entities/my-account/my-account.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'jhi-account-widget',
  templateUrl: './account-widget.component.html',
  styles: []
})
export class AccountWidgetComponent implements OnInit {

  @Input('accountId') accountId;
  private account: MyAccount;

  constructor(private accService: MyAccountService) {
  }

  ngOnInit() {
    console.log('on init called')
    this.load()
  }

  load() {
    this.accService.find(this.accountId).subscribe((account) => {
        this.account = account;
        console.log('loaded account object ', this.account);
    });
  }
}
