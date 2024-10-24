import {createAsyncThunk} from "@reduxjs/toolkit";
import {executePromise} from "../util/sliceUtil";
import {OrderApi} from "../api/order/OrderApi";

export const getMenus = createAsyncThunk("order/getMenus",
    (category:string) => executePromise(OrderApi.getMenus(category))
);
