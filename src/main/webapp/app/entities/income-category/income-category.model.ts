import { BaseEntity } from './../../shared';

export class IncomeCategory implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public parentId?: number,
        public subCategories?: BaseEntity[],
    ) {
    }
}
