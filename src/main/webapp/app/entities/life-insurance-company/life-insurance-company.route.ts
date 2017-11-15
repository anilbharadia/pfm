import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LifeInsuranceCompanyComponent } from './life-insurance-company.component';
import { LifeInsuranceCompanyDetailComponent } from './life-insurance-company-detail.component';
import { LifeInsuranceCompanyPopupComponent } from './life-insurance-company-dialog.component';
import { LifeInsuranceCompanyDeletePopupComponent } from './life-insurance-company-delete-dialog.component';

@Injectable()
export class LifeInsuranceCompanyResolvePagingParams implements Resolve<any> {

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

export const lifeInsuranceCompanyRoute: Routes = [
    {
        path: 'life-insurance-company',
        component: LifeInsuranceCompanyComponent,
        resolve: {
            'pagingParams': LifeInsuranceCompanyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsuranceCompany.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'life-insurance-company/:id',
        component: LifeInsuranceCompanyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsuranceCompany.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lifeInsuranceCompanyPopupRoute: Routes = [
    {
        path: 'life-insurance-company-new',
        component: LifeInsuranceCompanyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsuranceCompany.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'life-insurance-company/:id/edit',
        component: LifeInsuranceCompanyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsuranceCompany.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'life-insurance-company/:id/delete',
        component: LifeInsuranceCompanyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.lifeInsuranceCompany.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
