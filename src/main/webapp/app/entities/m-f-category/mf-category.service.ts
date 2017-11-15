import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MFCategory } from './mf-category.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MFCategoryService {

    private resourceUrl = SERVER_API_URL + 'api/m-f-categories';

    constructor(private http: Http) { }

    create(mFCategory: MFCategory): Observable<MFCategory> {
        const copy = this.convert(mFCategory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mFCategory: MFCategory): Observable<MFCategory> {
        const copy = this.convert(mFCategory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MFCategory> {
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
     * Convert a returned JSON object to MFCategory.
     */
    private convertItemFromServer(json: any): MFCategory {
        const entity: MFCategory = Object.assign(new MFCategory(), json);
        return entity;
    }

    /**
     * Convert a MFCategory to a JSON which can be sent to the server.
     */
    private convert(mFCategory: MFCategory): MFCategory {
        const copy: MFCategory = Object.assign({}, mFCategory);
        return copy;
    }
}
