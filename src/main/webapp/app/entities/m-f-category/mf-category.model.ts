import { BaseEntity } from './../../shared';

export class MFCategory implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public parentId?: number,
    ) {
    }
}
