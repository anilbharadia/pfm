import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    AMCService,
    AMCPopupService,
    AMCComponent,
    AMCDetailComponent,
    AMCDialogComponent,
    AMCPopupComponent,
    AMCDeletePopupComponent,
    AMCDeleteDialogComponent,
    aMCRoute,
    aMCPopupRoute,
    AMCResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...aMCRoute,
    ...aMCPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AMCComponent,
        AMCDetailComponent,
        AMCDialogComponent,
        AMCDeleteDialogComponent,
        AMCPopupComponent,
        AMCDeletePopupComponent,
    ],
    entryComponents: [
        AMCComponent,
        AMCDialogComponent,
        AMCPopupComponent,
        AMCDeleteDialogComponent,
        AMCDeletePopupComponent,
    ],
    providers: [
        AMCService,
        AMCPopupService,
        AMCResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmAMCModule {}
