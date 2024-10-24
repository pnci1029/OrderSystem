import {useCallback} from "react";
import {useDispatch} from "react-redux";
import {getMenus} from "../../../slice/orderSlice";

export function useOrderGetter() {
    const dispatch = useDispatch<any>();

    const getMenu = useCallback(async () => {
        try {
            const result: any = await dispatch(getMenus("gg")).unwrap();

            return result;
        } catch (e) {
            console.log('error message : ', e)
        }finally {
        }

        // eslint-disable-next-line
    }, []);
    return{
        getMenu
    }
}