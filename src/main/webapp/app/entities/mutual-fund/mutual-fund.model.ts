import { BaseEntity } from './../../shared';

export class MutualFund implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public manager?: string,
        public amcId?: number,
        public categoryId?: number,
    ) {
    }
}
