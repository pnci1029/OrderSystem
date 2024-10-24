import {AxiosResponse} from "axios";

export const executePromise = async <T>(f: () => Promise<AxiosResponse<T>>) => {
    try {
        return await f();
    } catch (error: any) {
        throw new Error(JSON.stringify(error?.response?.data));
    }
};