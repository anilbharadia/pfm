import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    MFInvestmentService,
    MFInvestmentPopupService,
    MFInvestmentComponent,
    MFInvestmentDetailComponent,
    MFInvestmentDialogComponent,
    MFInvestmentPopupComponent,
    MFInvestmentDeletePopupComponent,
    MFInvestmentDeleteDialogComponent,
    mFInvestmentRoute,
    mFInvestmentPopupRoute,
    MFInvestmentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mFInvestmentRoute,
    ...mFInvestmentPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MFInvestmentComponent,
        MFInvestmentDetailComponent,
        MFInvestmentDialogComponent,
        MFInvestmentDeleteDialogComponent,
        MFInvestmentPopupComponent,
        MFInvestmentDeletePopupComponent,
    ],
    entryComponents: [
        MFInvestmentComponent,
        MFInvestmentDialogComponent,
        MFInvestmentPopupComponent,
        MFInvestmentDeleteDialogComponent,
        MFInvestmentDeletePopupComponent,
    ],
    providers: [
        MFInvestmentService,
        MFInvestmentPopupService,
        MFInvestmentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmMFInvestmentModule {}
