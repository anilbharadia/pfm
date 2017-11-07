import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PfmBankModule } from './bank/bank.module';
import { PfmPersonModule } from './person/person.module';
import { PfmBankAccountModule } from './bank-account/bank-account.module';
import { PfmMyAccountModule } from './my-account/my-account.module';
import { PfmTransactionModule } from './transaction/transaction.module';
import { PfmTransactionCategoryModule } from './transaction-category/transaction-category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PfmBankModule,
        PfmPersonModule,
        PfmBankAccountModule,
        PfmMyAccountModule,
        PfmTransactionModule,
        PfmTransactionCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmEntityModule {}
