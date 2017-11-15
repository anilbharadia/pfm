import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    RecurringDepositService,
    RecurringDepositPopupService,
    RecurringDepositComponent,
    RecurringDepositDetailComponent,
    RecurringDepositDialogComponent,
    RecurringDepositPopupComponent,
    RecurringDepositDeletePopupComponent,
    RecurringDepositDeleteDialogComponent,
    recurringDepositRoute,
    recurringDepositPopupRoute,
    RecurringDepositResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...recurringDepositRoute,
    ...recurringDepositPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RecurringDepositComponent,
        RecurringDepositDetailComponent,
        RecurringDepositDialogComponent,
        RecurringDepositDeleteDialogComponent,
        RecurringDepositPopupComponent,
        RecurringDepositDeletePopupComponent,
    ],
    entryComponents: [
        RecurringDepositComponent,
        RecurringDepositDialogComponent,
        RecurringDepositPopupComponent,
        RecurringDepositDeleteDialogComponent,
        RecurringDepositDeletePopupComponent,
    ],
    providers: [
        RecurringDepositService,
        RecurringDepositPopupService,
        RecurringDepositResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmRecurringDepositModule {}
