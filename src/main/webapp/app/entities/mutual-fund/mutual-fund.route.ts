import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MutualFundComponent } from './mutual-fund.component';
import { MutualFundDetailComponent } from './mutual-fund-detail.component';
import { MutualFundPopupComponent } from './mutual-fund-dialog.component';
import { MutualFundDeletePopupComponent } from './mutual-fund-delete-dialog.component';

@Injectable()
export class MutualFundResolvePagingParams implements Resolve<any> {

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

export const mutualFundRoute: Routes = [
    {
        path: 'mutual-fund',
        component: MutualFundComponent,
        resolve: {
            'pagingParams': MutualFundResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mutualFund.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mutual-fund/:id',
        component: MutualFundDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mutualFund.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mutualFundPopupRoute: Routes = [
    {
        path: 'mutual-fund-new',
        component: MutualFundPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mutualFund.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mutual-fund/:id/edit',
        component: MutualFundPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mutualFund.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mutual-fund/:id/delete',
        component: MutualFundDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mutualFund.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
