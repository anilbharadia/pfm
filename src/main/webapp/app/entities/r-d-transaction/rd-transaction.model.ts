import { BaseEntity } from './../../shared';

export const enum RDTransactionType {
    'INSTALLMENT',
    'INTEREST'
}

export class RDTransaction implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public type?: RDTransactionType,
        public recurringDepositId?: number,
    ) {
    }
}
