import { BaseEntity } from './../../shared';

export class Transaction implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public desc?: string,
        public date?: any,
        public isTransfer?: boolean,
        public accountId?: number,
        public txTypeId?: number,
        public expenseCategoryId?: number,
        public incomeCategoryId?: number,
    ) {
        this.isTransfer = false;
    }
}
