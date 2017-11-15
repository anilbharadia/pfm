import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RDTransactionComponent } from './rd-transaction.component';
import { RDTransactionDetailComponent } from './rd-transaction-detail.component';
import { RDTransactionPopupComponent } from './rd-transaction-dialog.component';
import { RDTransactionDeletePopupComponent } from './rd-transaction-delete-dialog.component';

@Injectable()
export class RDTransactionResolvePagingParams implements Resolve<any> {

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

export const rDTransactionRoute: Routes = [
    {
        path: 'rd-transaction',
        component: RDTransactionComponent,
        resolve: {
            'pagingParams': RDTransactionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.rDTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rd-transaction/:id',
        component: RDTransactionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.rDTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rDTransactionPopupRoute: Routes = [
    {
        path: 'rd-transaction-new',
        component: RDTransactionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.rDTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rd-transaction/:id/edit',
        component: RDTransactionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.rDTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rd-transaction/:id/delete',
        component: RDTransactionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.rDTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
