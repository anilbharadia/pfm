import { BaseEntity } from './../../shared';

export class LifeInsurancePolicy implements BaseEntity {
    constructor(
        public id?: number,
        public policyNumber?: string,
        public name?: string,
        public totalPremiumPaid?: number,
        public premium?: number,
        public sumAssured?: number,
        public companyId?: number,
        public ownerId?: number,
        public goalId?: number,
    ) {
    }
}
