import { BaseEntity } from './../../shared';
import { PPFAccount } from '../p-pf-account/index';

export const enum PPFTransactionType {
    'INSTALLMENT',
    'INTEREST'
}

export class PPFTransaction implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public desc?: string,
        public amount?: number,
        public type?: PPFTransactionType,
        public account?:PPFAccount,
        public accountId?: number,
    ) {
    }
}
