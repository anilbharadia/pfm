import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FixedDepositComponent } from './fixed-deposit.component';
import { FixedDepositDetailComponent } from './fixed-deposit-detail.component';
import { FixedDepositPopupComponent } from './fixed-deposit-dialog.component';
import { FixedDepositDeletePopupComponent } from './fixed-deposit-delete-dialog.component';

@Injectable()
export class FixedDepositResolvePagingParams implements Resolve<any> {

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

export const fixedDepositRoute: Routes = [
    {
        path: 'fixed-deposit',
        component: FixedDepositComponent,
        resolve: {
            'pagingParams': FixedDepositResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.fixedDeposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fixed-deposit/:id',
        component: FixedDepositDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.fixedDeposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fixedDepositPopupRoute: Routes = [
    {
        path: 'fixed-deposit-new',
        component: FixedDepositPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.fixedDeposit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fixed-deposit/:id/edit',
        component: FixedDepositPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.fixedDeposit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fixed-deposit/:id/delete',
        component: FixedDepositDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.fixedDeposit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
