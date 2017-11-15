import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LifeInsurancePolicyComponent } from './life-insurance-policy.component';
import { LifeInsurancePolicyDetailComponent } from './life-insurance-policy-detail.component';
import { LifeInsurancePolicyPopupComponent } from './life-insurance-policy-dialog.component';
import { LifeInsurancePolicyDeletePopupComponent } from './life-insurance-policy-delete-dialog.component';

@Injectable()
export class LifeInsurancePolicyResolvePagingParams implements Resolve<any> {

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

export const lifeInsurancePolicyRoute: Routes = [
    {
        path: 'life-insurance-policy',
        component: LifeInsurancePolicyComponent,
        resolve: {
            'pagingParams': LifeInsurancePolicyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'life-insurance-policy/:id',
        component: LifeInsurancePolicyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lifeInsurancePolicyPopupRoute: Routes = [
    {
        path: 'life-insurance-policy-new',
        component: LifeInsurancePolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'life-insurance-policy/:id/edit',
        component: LifeInsurancePolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'life-insurance-policy/:id/delete',
        component: LifeInsurancePolicyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsurancePolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
