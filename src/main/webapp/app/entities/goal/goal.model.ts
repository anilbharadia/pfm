import { BaseEntity } from './../../shared';

export class Goal implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public amount?: number,
        public dueDate?: any,
        public balance?: number,
    ) {
    }
}
