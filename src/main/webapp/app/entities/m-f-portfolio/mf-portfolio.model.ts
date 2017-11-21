import { Person } from './../person/person.model';
import { AMC } from './../a-mc/amc.model';
import { BaseEntity } from './../../shared';

export class MFPortfolio implements BaseEntity {
    constructor(
        public id?: number,
        public folioNumber?: string,
        public amc?: AMC,
        public amcId?: number,
        public owner?: Person,
        public ownerId?: number,
    ) {
    }
}
