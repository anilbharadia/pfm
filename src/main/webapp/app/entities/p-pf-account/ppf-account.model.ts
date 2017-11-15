import { BaseEntity } from './../../shared';

export class PPFAccount implements BaseEntity {
    constructor(
        public id?: number,
        public accountNumber?: string,
        public balance?: number,
        public bankId?: number,
        public ownerId?: number,
        public goalId?: number,
    ) {
    }
}
