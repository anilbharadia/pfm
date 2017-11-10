import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    IncomeCategoryService,
    IncomeCategoryPopupService,
    IncomeCategoryComponent,
    IncomeCategoryDetailComponent,
    IncomeCategoryDialogComponent,
    IncomeCategoryPopupComponent,
    IncomeCategoryDeletePopupComponent,
    IncomeCategoryDeleteDialogComponent,
    incomeCategoryRoute,
    incomeCategoryPopupRoute,
    IncomeCategoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...incomeCategoryRoute,
    ...incomeCategoryPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IncomeCategoryComponent,
        IncomeCategoryDetailComponent,
        IncomeCategoryDialogComponent,
        IncomeCategoryDeleteDialogComponent,
        IncomeCategoryPopupComponent,
        IncomeCategoryDeletePopupComponent,
    ],
    entryComponents: [
        IncomeCategoryComponent,
        IncomeCategoryDialogComponent,
        IncomeCategoryPopupComponent,
        IncomeCategoryDeleteDialogComponent,
        IncomeCategoryDeletePopupComponent,
    ],
    providers: [
        IncomeCategoryService,
        IncomeCategoryPopupService,
        IncomeCategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmIncomeCategoryModule {}
