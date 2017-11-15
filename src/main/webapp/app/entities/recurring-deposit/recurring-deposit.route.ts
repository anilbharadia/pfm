import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RecurringDepositComponent } from './recurring-deposit.component';
import { RecurringDepositDetailComponent } from './recurring-deposit-detail.component';
import { RecurringDepositPopupComponent } from './recurring-deposit-dialog.component';
import { RecurringDepositDeletePopupComponent } from './recurring-deposit-delete-dialog.component';

@Injectable()
export class RecurringDepositResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const recurringDepositRoute: Routes = [
    {
        path: 'recurring-deposit',
        component: RecurringDepositComponent,
        resolve: {
            'pagingParams': RecurringDepositResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.recurringDeposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'recurring-deposit/:id',
        component: RecurringDepositDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.recurringDeposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recurringDepositPopupRoute: Routes = [
    {
        path: 'recurring-deposit-new',
        component: RecurringDepositPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.recurringDeposit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recurring-deposit/:id/edit',
        component: RecurringDepositPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.recurringDeposit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recurring-deposit/:id/delete',
        component: RecurringDepositDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.recurringDeposit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
