import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfmSharedModule } from '../../shared';
import {
    LifeInsurancePolicyService,
    LifeInsurancePolicyPopupService,
    LifeInsurancePolicyComponent,
    LifeInsurancePolicyDetailComponent,
    LifeInsurancePolicyDialogComponent,
    LifeInsurancePolicyPopupComponent,
    LifeInsurancePolicyDeletePopupComponent,
    LifeInsurancePolicyDeleteDialogComponent,
    lifeInsurancePolicyRoute,
    lifeInsurancePolicyPopupRoute,
    LifeInsurancePolicyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lifeInsurancePolicyRoute,
    ...lifeInsurancePolicyPopupRoute,
];

@NgModule({
    imports: [
        PfmSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LifeInsurancePolicyComponent,
        LifeInsurancePolicyDetailComponent,
        LifeInsurancePolicyDialogComponent,
        LifeInsurancePolicyDeleteDialogComponent,
        LifeInsurancePolicyPopupComponent,
        LifeInsurancePolicyDeletePopupComponent,
    ],
    entryComponents: [
        LifeInsurancePolicyComponent,
        LifeInsurancePolicyDialogComponent,
        LifeInsurancePolicyPopupComponent,
        LifeInsurancePolicyDeleteDialogComponent,
        LifeInsurancePolicyDeletePopupComponent,
    ],
    providers: [
        LifeInsurancePolicyService,
        LifeInsurancePolicyPopupService,
        LifeInsurancePolicyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmLifeInsurancePolicyModule {}
