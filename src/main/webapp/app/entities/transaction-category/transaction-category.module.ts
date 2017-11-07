import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    TransactionCategoryService,
    TransactionCategoryPopupService,
    TransactionCategoryComponent,
    TransactionCategoryDetailComponent,
    TransactionCategoryDialogComponent,
    TransactionCategoryPopupComponent,
    TransactionCategoryDeletePopupComponent,
    TransactionCategoryDeleteDialogComponent,
    transactionCategoryRoute,
    transactionCategoryPopupRoute,
    TransactionCategoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...transactionCategoryRoute,
    ...transactionCategoryPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransactionCategoryComponent,
        TransactionCategoryDetailComponent,
        TransactionCategoryDialogComponent,
        TransactionCategoryDeleteDialogComponent,
        TransactionCategoryPopupComponent,
        TransactionCategoryDeletePopupComponent,
    ],
    entryComponents: [
        TransactionCategoryComponent,
        TransactionCategoryDialogComponent,
        TransactionCategoryPopupComponent,
        TransactionCategoryDeleteDialogComponent,
        TransactionCategoryDeletePopupComponent,
    ],
    providers: [
        TransactionCategoryService,
        TransactionCategoryPopupService,
        TransactionCategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmTransactionCategoryModule {}
