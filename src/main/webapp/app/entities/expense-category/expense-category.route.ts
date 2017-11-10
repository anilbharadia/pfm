import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExpenseCategoryComponent } from './expense-category.component';
import { ExpenseCategoryDetailComponent } from './expense-category-detail.component';
import { ExpenseCategoryPopupComponent } from './expense-category-dialog.component';
import { ExpenseCategoryDeletePopupComponent } from './expense-category-delete-dialog.component';

@Injectable()
export class ExpenseCategoryResolvePagingParams implements Resolve<any> {

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

export const expenseCategoryRoute: Routes = [
    {
        path: 'expense-category',
        component: ExpenseCategoryComponent,
        resolve: {
            'pagingParams': ExpenseCategoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.expenseCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'expense-category/:id',
        component: ExpenseCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.expenseCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const expenseCategoryPopupRoute: Routes = [
    {
        path: 'expense-category-new',
        component: ExpenseCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.expenseCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'expense-category/:id/edit',
        component: ExpenseCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.expenseCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'expense-category/:id/delete',
        component: ExpenseCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfmApp.expenseCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
