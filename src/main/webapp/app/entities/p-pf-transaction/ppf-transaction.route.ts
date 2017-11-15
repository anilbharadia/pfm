import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PPFTransactionComponent } from './ppf-transaction.component';
import { PPFTransactionDetailComponent } from './ppf-transaction-detail.component';
import { PPFTransactionPopupComponent } from './ppf-transaction-dialog.component';
import { PPFTransactionDeletePopupComponent } from './ppf-transaction-delete-dialog.component';

@Injectable()
export class PPFTransactionResolvePagingParams implements Resolve<any> {

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

export const pPFTransactionRoute: Routes = [
    {
        path: 'ppf-transaction',
        component: PPFTransactionComponent,
        resolve: {
            'pagingParams': PPFTransactionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ppf-transaction/:id',
        component: PPFTransactionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pPFTransactionPopupRoute: Routes = [
    {
        path: 'ppf-transaction-new',
        component: PPFTransactionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ppf-transaction/:id/edit',
        component: PPFTransactionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ppf-transaction/:id/delete',
        component: PPFTransactionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
