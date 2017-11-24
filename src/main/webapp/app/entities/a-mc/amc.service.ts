import { MAX_PAGE_SIZE } from './../../shared/constants/pagination.constants';
import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AMC } from './amc.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AMCService {

    private resourceUrl = SERVER_API_URL + 'api/a-mcs';

    constructor(private http: Http) { }

    create(aMC: AMC): Observable<AMC> {
        const copy = this.convert(aMC);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(aMC: AMC): Observable<AMC> {
        const copy = this.convert(aMC);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AMC> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findAll(req?: any): Observable<ResponseWrapper> {
        
        req = req || {};
        req.size = MAX_PAGE_SIZE;
        
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
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
     * Convert a returned JSON object to AMC.
     */
    private convertItemFromServer(json: any): AMC {
        const entity: AMC = Object.assign(new AMC(), json);
        return entity;
    }

    /**
     * Convert a AMC to a JSON which can be sent to the server.
     */
    private convert(aMC: AMC): AMC {
        const copy: AMC = Object.assign({}, aMC);
        return copy;
    }
}
