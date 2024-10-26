import {MainApi} from "./MainApi";
import {OrderCreateRequestDto} from "../../types/order";

export class OrderApi {
    static baseUrl = `${MainApi.urlPrefix}`;

    static getMenus = (category:string) => () =>
        MainApi.api.post(`${OrderApi.baseUrl}/category&=${category}`);

    static createOrder = (order:OrderCreateRequestDto) => () =>
        MainApi.api.post(`${OrderApi.baseUrl}/order`, order);

    static updateOrder = (orderId: number) => () =>
        MainApi.api.patch(`${OrderApi.baseUrl}/order?orderId=${orderId}`);
}