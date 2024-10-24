import {createAsyncThunk} from "@reduxjs/toolkit";
import {executePromise} from "../util/sliceUtil";
import {OrderApi} from "../api/order/OrderApi";

export const getItems = createAsyncThunk("order/getItems",
    (category:string) => executePromise(OrderApi.getItems(category))
);
