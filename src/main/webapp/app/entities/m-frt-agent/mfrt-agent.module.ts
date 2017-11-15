import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    MFRTAgentService,
    MFRTAgentPopupService,
    MFRTAgentComponent,
    MFRTAgentDetailComponent,
    MFRTAgentDialogComponent,
    MFRTAgentPopupComponent,
    MFRTAgentDeletePopupComponent,
    MFRTAgentDeleteDialogComponent,
    mFRTAgentRoute,
    mFRTAgentPopupRoute,
    MFRTAgentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mFRTAgentRoute,
    ...mFRTAgentPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MFRTAgentComponent,
        MFRTAgentDetailComponent,
        MFRTAgentDialogComponent,
        MFRTAgentDeleteDialogComponent,
        MFRTAgentPopupComponent,
        MFRTAgentDeletePopupComponent,
    ],
    entryComponents: [
        MFRTAgentComponent,
        MFRTAgentDialogComponent,
        MFRTAgentPopupComponent,
        MFRTAgentDeleteDialogComponent,
        MFRTAgentDeletePopupComponent,
    ],
    providers: [
        MFRTAgentService,
        MFRTAgentPopupService,
        MFRTAgentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmMFRTAgentModule {}
