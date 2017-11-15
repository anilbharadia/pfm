import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MFCategoryComponent } from './mf-category.component';
import { MFCategoryDetailComponent } from './mf-category-detail.component';
import { MFCategoryPopupComponent } from './mf-category-dialog.component';
import { MFCategoryDeletePopupComponent } from './mf-category-delete-dialog.component';

@Injectable()
export class MFCategoryResolvePagingParams implements Resolve<any> {

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

export const mFCategoryRoute: Routes = [
    {
        path: 'mf-category',
        component: MFCategoryComponent,
        resolve: {
            'pagingParams': MFCategoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mf-category/:id',
        component: MFCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mFCategoryPopupRoute: Routes = [
    {
        path: 'mf-category-new',
        component: MFCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mf-category/:id/edit',
        component: MFCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mf-category/:id/delete',
        component: MFCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.mFCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
