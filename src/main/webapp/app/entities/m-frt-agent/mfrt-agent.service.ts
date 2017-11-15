import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MFRTAgent } from './mfrt-agent.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MFRTAgentService {

    private resourceUrl = SERVER_API_URL + 'api/m-frt-agents';

    constructor(private http: Http) { }

    create(mFRTAgent: MFRTAgent): Observable<MFRTAgent> {
        const copy = this.convert(mFRTAgent);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mFRTAgent: MFRTAgent): Observable<MFRTAgent> {
        const copy = this.convert(mFRTAgent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MFRTAgent> {
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
     * Convert a returned JSON object to MFRTAgent.
     */
    private convertItemFromServer(json: any): MFRTAgent {
        const entity: MFRTAgent = Object.assign(new MFRTAgent(), json);
        return entity;
    }

    /**
     * Convert a MFRTAgent to a JSON which can be sent to the server.
     */
    private convert(mFRTAgent: MFRTAgent): MFRTAgent {
        const copy: MFRTAgent = Object.assign({}, mFRTAgent);
        return copy;
    }
}
