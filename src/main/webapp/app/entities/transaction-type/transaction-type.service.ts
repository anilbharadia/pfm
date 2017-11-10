import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TransactionType } from './transaction-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TransactionTypeService {

    private resourceUrl = SERVER_API_URL + 'api/transaction-types';

    constructor(private http: Http) { }

    create(transactionType: TransactionType): Observable<TransactionType> {
        const copy = this.convert(transactionType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(transactionType: TransactionType): Observable<TransactionType> {
        const copy = this.convert(transactionType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TransactionType> {
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
     * Convert a returned JSON object to TransactionType.
     */
    private convertItemFromServer(json: any): TransactionType {
        const entity: TransactionType = Object.assign(new TransactionType(), json);
        return entity;
    }

    /**
     * Convert a TransactionType to a JSON which can be sent to the server.
     */
    private convert(transactionType: TransactionType): TransactionType {
        const copy: TransactionType = Object.assign({}, transactionType);
        return copy;
    }
}
