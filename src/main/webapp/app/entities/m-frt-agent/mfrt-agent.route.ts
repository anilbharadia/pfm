import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MFRTAgentComponent } from './mfrt-agent.component';
import { MFRTAgentDetailComponent } from './mfrt-agent-detail.component';
import { MFRTAgentPopupComponent } from './mfrt-agent-dialog.component';
import { MFRTAgentDeletePopupComponent } from './mfrt-agent-delete-dialog.component';

@Injectable()
export class MFRTAgentResolvePagingParams implements Resolve<any> {

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

export const mFRTAgentRoute: Routes = [
    {
        path: 'mfrt-agent',
        component: MFRTAgentComponent,
        resolve: {
            'pagingParams': MFRTAgentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFRTAgent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mfrt-agent/:id',
        component: MFRTAgentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFRTAgent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mFRTAgentPopupRoute: Routes = [
    {
        path: 'mfrt-agent-new',
        component: MFRTAgentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFRTAgent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mfrt-agent/:id/edit',
        component: MFRTAgentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFRTAgent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mfrt-agent/:id/delete',
        component: MFRTAgentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFRTAgent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
