import { BaseEntity } from './../../shared';

export class ExpenseCategory implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public parentId?: number,
        public subCategories?: BaseEntity[],
    ) {
    }
}
