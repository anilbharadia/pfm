import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper } from './../shared/model/response-wrapper.model';
import { MyAccountService } from './../entities/my-account/my-account.service';
import { MyAccount } from './../entities/my-account/my-account.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-accounts-widget',
  templateUrl: './accounts-widget.component.html',
  styles: []
})
export class AccountsWidgetComponent implements OnInit {

  private accounts: MyAccount[];

  constructor(
    private accountService: MyAccountService,
    private jhiAlertService: JhiAlertService) {
    this.load();
  }

  ngOnInit() {
  }

  load() {
    this.accountService.query().subscribe(
      (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
      (res: ResponseWrapper) => this.onError(res.json)
    );
  }

  private onSuccess(data, headers) {
    this.accounts = data;
  }
  private onError(error) {
      this.jhiAlertService.error(error.message, null, null);
  }

}
