import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IncomeCategoryComponent } from './income-category.component';
import { IncomeCategoryDetailComponent } from './income-category-detail.component';
import { IncomeCategoryPopupComponent } from './income-category-dialog.component';
import { IncomeCategoryDeletePopupComponent } from './income-category-delete-dialog.component';

@Injectable()
export class IncomeCategoryResolvePagingParams implements Resolve<any> {

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

export const incomeCategoryRoute: Routes = [
    {
        path: 'income-category',
        component: IncomeCategoryComponent,
        resolve: {
            'pagingParams': IncomeCategoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.incomeCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'income-category/:id',
        component: IncomeCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.incomeCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incomeCategoryPopupRoute: Routes = [
    {
        path: 'income-category-new',
        component: IncomeCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.incomeCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'income-category/:id/edit',
        component: IncomeCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.incomeCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'income-category/:id/delete',
        component: IncomeCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.incomeCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
