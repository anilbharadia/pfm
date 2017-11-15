import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    PPFTransactionService,
    PPFTransactionPopupService,
    PPFTransactionComponent,
    PPFTransactionDetailComponent,
    PPFTransactionDialogComponent,
    PPFTransactionPopupComponent,
    PPFTransactionDeletePopupComponent,
    PPFTransactionDeleteDialogComponent,
    pPFTransactionRoute,
    pPFTransactionPopupRoute,
    PPFTransactionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pPFTransactionRoute,
    ...pPFTransactionPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PPFTransactionComponent,
        PPFTransactionDetailComponent,
        PPFTransactionDialogComponent,
        PPFTransactionDeleteDialogComponent,
        PPFTransactionPopupComponent,
        PPFTransactionDeletePopupComponent,
    ],
    entryComponents: [
        PPFTransactionComponent,
        PPFTransactionDialogComponent,
        PPFTransactionPopupComponent,
        PPFTransactionDeleteDialogComponent,
        PPFTransactionDeletePopupComponent,
    ],
    providers: [
        PPFTransactionService,
        PPFTransactionPopupService,
        PPFTransactionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmPPFTransactionModule {}
