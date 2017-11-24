import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MFInvestmentComponent } from './mf-investment.component';
import { MFInvestmentDetailComponent } from './mf-investment-detail.component';
import { MFInvestmentPopupComponent } from './mf-investment-dialog.component';
import { MFInvestmentDeletePopupComponent } from './mf-investment-delete-dialog.component';

@Injectable()
export class MFInvestmentResolvePagingParams implements Resolve<any> {

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

export const mFInvestmentRoute: Routes = [
    {
        path: 'mf-investment',
        component: MFInvestmentComponent,
        resolve: {
            'pagingParams': MFInvestmentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFInvestment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mf-investment/:id',
        component: MFInvestmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFInvestment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mFInvestmentPopupRoute: Routes = [
    {
        path: 'mf-investment-new',
        component: MFInvestmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFInvestment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mf-investment-new/:goalId',
        component: MFInvestmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFInvestment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mf-investment/:id/edit',
        component: MFInvestmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFInvestment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mf-investment/:id/delete',
        component: MFInvestmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFInvestment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
