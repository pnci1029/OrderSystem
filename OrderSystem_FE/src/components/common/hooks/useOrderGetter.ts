import {useCallback} from "react";
import {useDispatch} from "react-redux";
import {getMenus} from "../../../slice/orderSlice";

export function useOrderGetter() {
    const dispatch = useDispatch<any>();

    const getMenu = useCallback(async () => {
        try {
            return await dispatch(getMenus("gg")).unwrap();
        } catch (e) {
            console.log('error message : ', e)
        }
        // eslint-disable-next-line
    }, []);

    return {
        getMenu
    }
}