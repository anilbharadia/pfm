import { BaseEntity } from './../../shared';

export class MFInvestment implements BaseEntity {
    constructor(
        public id?: number,
        public purchaseDate?: any,
        public navDate?: any,
        public amount?: number,
        public nav?: number,
        public unit?: number,
        public amcId?: number,
        public fundId?: number,
        public folioId?: number,
        public goalId?: number,
    ) {
    }
}
