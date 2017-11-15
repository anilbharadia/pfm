import { BaseEntity } from './../../shared';

export class AMC implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public website?: string,
        public logoURL?: string,
        public mfrtAgentId?: number,
    ) {
    }
}
