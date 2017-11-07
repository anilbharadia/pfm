import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TransactionCategoryComponent } from './transaction-category.component';
import { TransactionCategoryDetailComponent } from './transaction-category-detail.component';
import { TransactionCategoryPopupComponent } from './transaction-category-dialog.component';
import { TransactionCategoryDeletePopupComponent } from './transaction-category-delete-dialog.component';

@Injectable()
export class TransactionCategoryResolvePagingParams implements Resolve<any> {

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

export const transactionCategoryRoute: Routes = [
    {
        path: 'transaction-category',
        component: TransactionCategoryComponent,
        resolve: {
            'pagingParams': TransactionCategoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'transaction-category/:id',
        component: TransactionCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transactionCategoryPopupRoute: Routes = [
    {
        path: 'transaction-category-new',
        component: TransactionCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction-category/:id/edit',
        component: TransactionCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction-category/:id/delete',
        component: TransactionCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.transactionCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
