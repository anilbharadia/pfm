import { BaseEntity } from './../../shared';

export const enum FDStatus {
    'OPEN',
    'CLOSED'
}

export class FixedDeposit implements BaseEntity {
    constructor(
        public id?: number,
        public accountNumber?: string,
        public amount?: number,
        public openDate?: any,
        public maturityDate?: any,
        public status?: FDStatus,
        public bankId?: number,
        public goalId?: number,
        public ownerId?: number,
    ) {
    }
}
