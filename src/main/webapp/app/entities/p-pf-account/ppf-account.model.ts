import { Goal } from './../goal/goal.model';
import { Person } from './../person/person.model';
import { Bank } from './../bank/bank.model';
import { BaseEntity } from './../../shared';

export class PPFAccount implements BaseEntity {
    constructor(
        public id?: number,
        public accountNumber?: string,
        public balance?: number,
        public bank?: Bank,
        public bankId?: number,
        public owner?: Person,
        public ownerId?: number,
        public goal?: Goal,
        public goalId?: number,
    ) {
    }
}
