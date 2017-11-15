import { BaseEntity } from './../../shared';

export class LifeInsuranceCompany implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
