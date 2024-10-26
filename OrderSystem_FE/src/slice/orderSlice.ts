import {createAsyncThunk} from "@reduxjs/toolkit";
import {executePromise} from "../util/sliceUtil";
import {OrderApi} from "../api/order/OrderApi";
import {OrderCreateRequestDto} from "../types/order";

export const getMenus = createAsyncThunk("order/getMenus",
    (category:string) => executePromise(OrderApi.getMenus(category))
);

export const createOrder = createAsyncThunk("order/createOrder",
    (order:OrderCreateRequestDto) => executePromise(OrderApi.createOrder(order))
);
export const updateOrder = createAsyncThunk("order/updateOrder",
    (orderId:number) => executePromise(OrderApi.updateOrder(orderId))
);
