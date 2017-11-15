import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    LifeInsuranceCompanyService,
    LifeInsuranceCompanyPopupService,
    LifeInsuranceCompanyComponent,
    LifeInsuranceCompanyDetailComponent,
    LifeInsuranceCompanyDialogComponent,
    LifeInsuranceCompanyPopupComponent,
    LifeInsuranceCompanyDeletePopupComponent,
    LifeInsuranceCompanyDeleteDialogComponent,
    lifeInsuranceCompanyRoute,
    lifeInsuranceCompanyPopupRoute,
    LifeInsuranceCompanyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lifeInsuranceCompanyRoute,
    ...lifeInsuranceCompanyPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LifeInsuranceCompanyComponent,
        LifeInsuranceCompanyDetailComponent,
        LifeInsuranceCompanyDialogComponent,
        LifeInsuranceCompanyDeleteDialogComponent,
        LifeInsuranceCompanyPopupComponent,
        LifeInsuranceCompanyDeletePopupComponent,
    ],
    entryComponents: [
        LifeInsuranceCompanyComponent,
        LifeInsuranceCompanyDialogComponent,
        LifeInsuranceCompanyPopupComponent,
        LifeInsuranceCompanyDeleteDialogComponent,
        LifeInsuranceCompanyDeletePopupComponent,
    ],
    providers: [
        LifeInsuranceCompanyService,
        LifeInsuranceCompanyPopupService,
        LifeInsuranceCompanyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmLifeInsuranceCompanyModule {}
