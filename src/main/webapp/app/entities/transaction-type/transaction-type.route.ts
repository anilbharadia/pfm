import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TransactionTypeComponent } from './transaction-type.component';
import { TransactionTypeDetailComponent } from './transaction-type-detail.component';
import { TransactionTypePopupComponent } from './transaction-type-dialog.component';
import { TransactionTypeDeletePopupComponent } from './transaction-type-delete-dialog.component';

@Injectable()
export class TransactionTypeResolvePagingParams implements Resolve<any> {

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

export const transactionTypeRoute: Routes = [
    {
        path: 'transaction-type',
        component: TransactionTypeComponent,
        resolve: {
            'pagingParams': TransactionTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'transaction-type/:id',
        component: TransactionTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transactionTypePopupRoute: Routes = [
    {
        path: 'transaction-type-new',
        component: TransactionTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction-type/:id/edit',
        component: TransactionTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction-type/:id/delete',
        component: TransactionTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
