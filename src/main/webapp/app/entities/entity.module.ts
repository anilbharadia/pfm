import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PfmBankModule } from './bank/bank.module';
import { PfmPersonModule } from './person/person.module';
import { PfmBankAccountModule } from './bank-account/bank-account.module';
import { PfmMyAccountModule } from './my-account/my-account.module';
import { PfmTransactionModule } from './transaction/transaction.module';
import { PfmTransactionCategoryModule } from './transaction-category/transaction-category.module';
import { PfmTransactionTypeModule } from './transaction-type/transaction-type.module';
import { PfmExpenseCategoryModule } from './expense-category/expense-category.module';
import { PfmIncomeCategoryModule } from './income-category/income-category.module';
import { PfmGoalModule } from './goal/goal.module';
import { PfmMFRTAgentModule } from './m-frt-agent/mfrt-agent.module';
import { PfmMFCategoryModule } from './m-f-category/mf-category.module';
import { PfmAMCModule } from './a-mc/amc.module';
import { PfmMutualFundModule } from './mutual-fund/mutual-fund.module';
import { PfmMFPortfolioModule } from './m-f-portfolio/mf-portfolio.module';
import { PfmMFInvestmentModule } from './m-f-investment/mf-investment.module';
import { PfmFixedDepositModule } from './fixed-deposit/fixed-deposit.module';
import { PfmRecurringDepositModule } from './recurring-deposit/recurring-deposit.module';
import { PfmRDTransactionModule } from './r-d-transaction/rd-transaction.module';
import { PfmPPFAccountModule } from './p-pf-account/ppf-account.module';
import { PfmPPFTransactionModule } from './p-pf-transaction/ppf-transaction.module';
import { PfmLifeInsuranceCompanyModule } from './life-insurance-company/life-insurance-company.module';
import { PfmLifeInsurancePolicyModule } from './life-insurance-policy/life-insurance-policy.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PfmBankModule,
        PfmPersonModule,
        PfmBankAccountModule,
        PfmMyAccountModule,
        PfmTransactionModule,
        PfmTransactionCategoryModule,
        PfmTransactionTypeModule,
        PfmExpenseCategoryModule,
        PfmIncomeCategoryModule,
        PfmGoalModule,
        PfmMFRTAgentModule,
        PfmMFCategoryModule,
        PfmAMCModule,
        PfmMutualFundModule,
        PfmMFPortfolioModule,
        PfmMFInvestmentModule,
        PfmFixedDepositModule,
        PfmRecurringDepositModule,
        PfmRDTransactionModule,
        PfmPPFAccountModule,
        PfmPPFTransactionModule,
        PfmLifeInsuranceCompanyModule,
        PfmLifeInsurancePolicyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfmEntityModule {}
