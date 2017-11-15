import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PPFAccountComponent } from './ppf-account.component';
import { PPFAccountDetailComponent } from './ppf-account-detail.component';
import { PPFAccountPopupComponent } from './ppf-account-dialog.component';
import { PPFAccountDeletePopupComponent } from './ppf-account-delete-dialog.component';

@Injectable()
export class PPFAccountResolvePagingParams implements Resolve<any> {

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

export const pPFAccountRoute: Routes = [
    {
        path: 'ppf-account',
        component: PPFAccountComponent,
        resolve: {
            'pagingParams': PPFAccountResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ppf-account/:id',
        component: PPFAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pPFAccountPopupRoute: Routes = [
    {
        path: 'ppf-account-new',
        component: PPFAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ppf-account/:id/edit',
        component: PPFAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ppf-account/:id/delete',
        component: PPFAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.pPFAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
