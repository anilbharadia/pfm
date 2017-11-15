import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    RDTransactionService,
    RDTransactionPopupService,
    RDTransactionComponent,
    RDTransactionDetailComponent,
    RDTransactionDialogComponent,
    RDTransactionPopupComponent,
    RDTransactionDeletePopupComponent,
    RDTransactionDeleteDialogComponent,
    rDTransactionRoute,
    rDTransactionPopupRoute,
    RDTransactionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rDTransactionRoute,
    ...rDTransactionPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RDTransactionComponent,
        RDTransactionDetailComponent,
        RDTransactionDialogComponent,
        RDTransactionDeleteDialogComponent,
        RDTransactionPopupComponent,
        RDTransactionDeletePopupComponent,
    ],
    entryComponents: [
        RDTransactionComponent,
        RDTransactionDialogComponent,
        RDTransactionPopupComponent,
        RDTransactionDeleteDialogComponent,
        RDTransactionDeletePopupComponent,
    ],
    providers: [
        RDTransactionService,
        RDTransactionPopupService,
        RDTransactionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmRDTransactionModule {}
