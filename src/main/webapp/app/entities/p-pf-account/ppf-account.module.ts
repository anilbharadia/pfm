import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    PPFAccountService,
    PPFAccountPopupService,
    PPFAccountComponent,
    PPFAccountDetailComponent,
    PPFAccountDialogComponent,
    PPFAccountPopupComponent,
    PPFAccountDeletePopupComponent,
    PPFAccountDeleteDialogComponent,
    pPFAccountRoute,
    pPFAccountPopupRoute,
    PPFAccountResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pPFAccountRoute,
    ...pPFAccountPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PPFAccountComponent,
        PPFAccountDetailComponent,
        PPFAccountDialogComponent,
        PPFAccountDeleteDialogComponent,
        PPFAccountPopupComponent,
        PPFAccountDeletePopupComponent,
    ],
    entryComponents: [
        PPFAccountComponent,
        PPFAccountDialogComponent,
        PPFAccountPopupComponent,
        PPFAccountDeleteDialogComponent,
        PPFAccountDeletePopupComponent,
    ],
    providers: [
        PPFAccountService,
        PPFAccountPopupService,
        PPFAccountResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmPPFAccountModule {}
