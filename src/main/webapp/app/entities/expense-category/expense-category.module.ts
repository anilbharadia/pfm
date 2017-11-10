import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    ExpenseCategoryService,
    ExpenseCategoryPopupService,
    ExpenseCategoryComponent,
    ExpenseCategoryDetailComponent,
    ExpenseCategoryDialogComponent,
    ExpenseCategoryPopupComponent,
    ExpenseCategoryDeletePopupComponent,
    ExpenseCategoryDeleteDialogComponent,
    expenseCategoryRoute,
    expenseCategoryPopupRoute,
    ExpenseCategoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...expenseCategoryRoute,
    ...expenseCategoryPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExpenseCategoryComponent,
        ExpenseCategoryDetailComponent,
        ExpenseCategoryDialogComponent,
        ExpenseCategoryDeleteDialogComponent,
        ExpenseCategoryPopupComponent,
        ExpenseCategoryDeletePopupComponent,
    ],
    entryComponents: [
        ExpenseCategoryComponent,
        ExpenseCategoryDialogComponent,
        ExpenseCategoryPopupComponent,
        ExpenseCategoryDeleteDialogComponent,
        ExpenseCategoryDeletePopupComponent,
    ],
    providers: [
        ExpenseCategoryService,
        ExpenseCategoryPopupService,
        ExpenseCategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmExpenseCategoryModule {}
