import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AMCComponent } from './amc.component';
import { AMCDetailComponent } from './amc-detail.component';
import { AMCPopupComponent } from './amc-dialog.component';
import { AMCDeletePopupComponent } from './amc-delete-dialog.component';

@Injectable()
export class AMCResolvePagingParams implements Resolve<any> {

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

export const aMCRoute: Routes = [
    {
        path: 'amc',
        component: AMCComponent,
        resolve: {
            'pagingParams': AMCResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.aMC.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'amc/:id',
        component: AMCDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.aMC.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aMCPopupRoute: Routes = [
    {
        path: 'amc-new',
        component: AMCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.aMC.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amc/:id/edit',
        component: AMCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.aMC.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amc/:id/delete',
        component: AMCDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.aMC.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
