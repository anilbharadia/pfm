import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    MFPortfolioService,
    MFPortfolioPopupService,
    MFPortfolioComponent,
    MFPortfolioDetailComponent,
    MFPortfolioDialogComponent,
    MFPortfolioPopupComponent,
    MFPortfolioDeletePopupComponent,
    MFPortfolioDeleteDialogComponent,
    mFPortfolioRoute,
    mFPortfolioPopupRoute,
    MFPortfolioResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mFPortfolioRoute,
    ...mFPortfolioPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MFPortfolioComponent,
        MFPortfolioDetailComponent,
        MFPortfolioDialogComponent,
        MFPortfolioDeleteDialogComponent,
        MFPortfolioPopupComponent,
        MFPortfolioDeletePopupComponent,
    ],
    entryComponents: [
        MFPortfolioComponent,
        MFPortfolioDialogComponent,
        MFPortfolioPopupComponent,
        MFPortfolioDeleteDialogComponent,
        MFPortfolioDeletePopupComponent,
    ],
    providers: [
        MFPortfolioService,
        MFPortfolioPopupService,
        MFPortfolioResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmMFPortfolioModule {}
