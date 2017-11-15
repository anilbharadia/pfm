import { BaseEntity } from './../../shared';

export const enum RDStatus {
    'OPEN',
    'CLOSED'
}

export class RecurringDeposit implements BaseEntity {
    constructor(
        public id?: number,
        public accountNumber?: string,
        public startDate?: any,
        public endDate?: any,
        public installmentDateDay?: number,
        public currentBalance?: number,
        public status?: RDStatus,
        public interestRate?: number,
        public bankId?: number,
        public goalId?: number,
        public ownerId?: number,
    ) {
    }
}
