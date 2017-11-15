import { BaseEntity } from './../../shared';

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
        public accountId?: number,
    ) {
    }
}
