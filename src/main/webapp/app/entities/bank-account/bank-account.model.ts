import { BaseEntity } from './../../shared';

export class BankAccount implements BaseEntity {
    constructor(
        public id?: number,
        public acNumber?: string,
        public ifsc?: string,
        public micr?: string,
        public bankId?: number,
        public accountId?: number,
    ) {
    }
}
