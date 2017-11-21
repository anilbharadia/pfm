import { IncomeCategory } from './../income-category/income-category.model';
import { ExpenseCategory } from './../expense-category/expense-category.model';
import { BaseEntity } from './../../shared';
import { MyAccount } from '../my-account/index';
import { TransactionType } from '../transaction-type/index';

export class Transaction implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public desc?: string,
        public date?: any,
        public isTransfer?: boolean,

        public account?: MyAccount,
        public accountId?: number,

        public transferAccountId?: number,

        public txType?: TransactionType,
        public txTypeId?: number,

        public expenseCategory?: ExpenseCategory,
        public expenseCategoryId?: number,

        public incomeCategory?: IncomeCategory,
        public incomeCategoryId?: number,
    ) {
        this.isTransfer = false;
    }
}
