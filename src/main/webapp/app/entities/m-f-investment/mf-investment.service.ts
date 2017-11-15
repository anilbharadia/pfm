import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MFInvestment } from './mf-investment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MFInvestmentService {

    private resourceUrl = SERVER_API_URL + 'api/m-f-investments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(mFInvestment: MFInvestment): Observable<MFInvestment> {
        const copy = this.convert(mFInvestment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mFInvestment: MFInvestment): Observable<MFInvestment> {
        const copy = this.convert(mFInvestment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MFInvestment> {
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
     * Convert a returned JSON object to MFInvestment.
     */
    private convertItemFromServer(json: any): MFInvestment {
        const entity: MFInvestment = Object.assign(new MFInvestment(), json);
        entity.purchaseDate = this.dateUtils
            .convertDateTimeFromServer(json.purchaseDate);
        entity.navDate = this.dateUtils
            .convertDateTimeFromServer(json.navDate);
        return entity;
    }

    /**
     * Convert a MFInvestment to a JSON which can be sent to the server.
     */
    private convert(mFInvestment: MFInvestment): MFInvestment {
        const copy: MFInvestment = Object.assign({}, mFInvestment);

        copy.purchaseDate = this.dateUtils.toDate(mFInvestment.purchaseDate);

        copy.navDate = this.dateUtils.toDate(mFInvestment.navDate);
        return copy;
    }
}
