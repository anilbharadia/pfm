import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    MFCategoryService,
    MFCategoryPopupService,
    MFCategoryComponent,
    MFCategoryDetailComponent,
    MFCategoryDialogComponent,
    MFCategoryPopupComponent,
    MFCategoryDeletePopupComponent,
    MFCategoryDeleteDialogComponent,
    mFCategoryRoute,
    mFCategoryPopupRoute,
    MFCategoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mFCategoryRoute,
    ...mFCategoryPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MFCategoryComponent,
        MFCategoryDetailComponent,
        MFCategoryDialogComponent,
        MFCategoryDeleteDialogComponent,
        MFCategoryPopupComponent,
        MFCategoryDeletePopupComponent,
    ],
    entryComponents: [
        MFCategoryComponent,
        MFCategoryDialogComponent,
        MFCategoryPopupComponent,
        MFCategoryDeleteDialogComponent,
        MFCategoryDeletePopupComponent,
    ],
    providers: [
        MFCategoryService,
        MFCategoryPopupService,
        MFCategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmMFCategoryModule {}
