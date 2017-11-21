import { MFCategory } from './../m-f-category/mf-category.model';
import { AMC } from './../a-mc/amc.model';
import { BaseEntity } from './../../shared';

export class MutualFund implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public manager?: string,
        public amc?: AMC,
        public amcId?: number,
        public category?: MFCategory,
        public categoryId?: number,
    ) {
    }
}
