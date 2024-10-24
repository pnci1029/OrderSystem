import {MainApi} from "./MainApi";

export class OrderApi {
    static baseUrl = `${MainApi.urlPrefix}`;

    static getItems = (category:string) => () =>
        MainApi.api.post(`${OrderApi.baseUrl}/category&=${category}`);
}