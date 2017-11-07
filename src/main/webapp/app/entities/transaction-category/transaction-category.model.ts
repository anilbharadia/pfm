import { BaseEntity } from './../../shared';

export class TransactionCategory implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
