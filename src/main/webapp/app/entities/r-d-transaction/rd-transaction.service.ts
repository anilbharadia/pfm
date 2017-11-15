import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RDTransaction } from './rd-transaction.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RDTransactionService {

    private resourceUrl = SERVER_API_URL + 'api/r-d-transactions';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(rDTransaction: RDTransaction): Observable<RDTransaction> {
        const copy = this.convert(rDTransaction);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(rDTransaction: RDTransaction): Observable<RDTransaction> {
        const copy = this.convert(rDTransaction);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RDTransaction> {
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
     * Convert a returned JSON object to RDTransaction.
     */
    private convertItemFromServer(json: any): RDTransaction {
        const entity: RDTransaction = Object.assign(new RDTransaction(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a RDTransaction to a JSON which can be sent to the server.
     */
    private convert(rDTransaction: RDTransaction): RDTransaction {
        const copy: RDTransaction = Object.assign({}, rDTransaction);

        copy.date = this.dateUtils.toDate(rDTransaction.date);
        return copy;
    }
}
