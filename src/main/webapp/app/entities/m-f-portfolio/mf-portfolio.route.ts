import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MFPortfolioComponent } from './mf-portfolio.component';
import { MFPortfolioDetailComponent } from './mf-portfolio-detail.component';
import { MFPortfolioPopupComponent } from './mf-portfolio-dialog.component';
import { MFPortfolioDeletePopupComponent } from './mf-portfolio-delete-dialog.component';

@Injectable()
export class MFPortfolioResolvePagingParams implements Resolve<any> {

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

export const mFPortfolioRoute: Routes = [
    {
        path: 'mf-portfolio',
        component: MFPortfolioComponent,
        resolve: {
            'pagingParams': MFPortfolioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFPortfolio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mf-portfolio/:id',
        component: MFPortfolioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFPortfolio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mFPortfolioPopupRoute: Routes = [
    {
        path: 'mf-portfolio-new',
        component: MFPortfolioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFPortfolio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mf-portfolio/:id/edit',
        component: MFPortfolioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFPortfolio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mf-portfolio/:id/delete',
        component: MFPortfolioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFPortfolio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
