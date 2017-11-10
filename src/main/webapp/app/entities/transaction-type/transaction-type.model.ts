import { BaseEntity } from './../../shared';

export class TransactionType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
