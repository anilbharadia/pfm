import { BaseEntity } from './../../shared';

export class MFRTAgent implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
