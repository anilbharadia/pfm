import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { LifeInsurancePolicy } from './life-insurance-policy.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LifeInsurancePolicyService {

    private resourceUrl = SERVER_API_URL + 'api/life-insurance-policies';

    constructor(private http: Http) { }

    create(lifeInsurancePolicy: LifeInsurancePolicy): Observable<LifeInsurancePolicy> {
        const copy = this.convert(lifeInsurancePolicy);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(lifeInsurancePolicy: LifeInsurancePolicy): Observable<LifeInsurancePolicy> {
        const copy = this.convert(lifeInsurancePolicy);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LifeInsurancePolicy> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to LifeInsurancePolicy.
     */
    private convertItemFromServer(json: any): LifeInsurancePolicy {
        const entity: LifeInsurancePolicy = Object.assign(new LifeInsurancePolicy(), json);
        return entity;
    }

    /**
     * Convert a LifeInsurancePolicy to a JSON which can be sent to the server.
     */
    private convert(lifeInsurancePolicy: LifeInsurancePolicy): LifeInsurancePolicy {
        const copy: LifeInsurancePolicy = Object.assign({}, lifeInsurancePolicy);
        return copy;
    }
}
