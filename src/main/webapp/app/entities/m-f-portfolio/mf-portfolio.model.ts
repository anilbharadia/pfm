import { BaseEntity } from './../../shared';

export class MFPortfolio implements BaseEntity {
    constructor(
        public id?: number,
        public folioNumber?: string,
        public amcId?: number,
        public ownerId?: number,
    ) {
    }
}
