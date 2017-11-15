import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    FixedDepositService,
    FixedDepositPopupService,
    FixedDepositComponent,
    FixedDepositDetailComponent,
    FixedDepositDialogComponent,
    FixedDepositPopupComponent,
    FixedDepositDeletePopupComponent,
    FixedDepositDeleteDialogComponent,
    fixedDepositRoute,
    fixedDepositPopupRoute,
    FixedDepositResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fixedDepositRoute,
    ...fixedDepositPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FixedDepositComponent,
        FixedDepositDetailComponent,
        FixedDepositDialogComponent,
        FixedDepositDeleteDialogComponent,
        FixedDepositPopupComponent,
        FixedDepositDeletePopupComponent,
    ],
    entryComponents: [
        FixedDepositComponent,
        FixedDepositDialogComponent,
        FixedDepositPopupComponent,
        FixedDepositDeleteDialogComponent,
        FixedDepositDeletePopupComponent,
    ],
    providers: [
        FixedDepositService,
        FixedDepositPopupService,
        FixedDepositResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmFixedDepositModule {}
