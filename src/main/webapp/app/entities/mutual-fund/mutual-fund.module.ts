import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    MutualFundService,
    MutualFundPopupService,
    MutualFundComponent,
    MutualFundDetailComponent,
    MutualFundDialogComponent,
    MutualFundPopupComponent,
    MutualFundDeletePopupComponent,
    MutualFundDeleteDialogComponent,
    mutualFundRoute,
    mutualFundPopupRoute,
    MutualFundResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mutualFundRoute,
    ...mutualFundPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MutualFundComponent,
        MutualFundDetailComponent,
        MutualFundDialogComponent,
        MutualFundDeleteDialogComponent,
        MutualFundPopupComponent,
        MutualFundDeletePopupComponent,
    ],
    entryComponents: [
        MutualFundComponent,
        MutualFundDialogComponent,
        MutualFundPopupComponent,
        MutualFundDeleteDialogComponent,
        MutualFundDeletePopupComponent,
    ],
    providers: [
        MutualFundService,
        MutualFundPopupService,
        MutualFundResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmMutualFundModule {}
