import {Status} from "../../../types/order";
import {useCallback} from "react";
import {updateOrder} from "../../../slice/orderSlice";
import {useDispatch} from "react-redux";

export function useOrderFunctions() {
    const dispatch = useDispatch<any>();

    // 현재 상태의 다음 상태값 노출 함수
    const nextStatus = (status: Status) => {
        const statusKeys = Object.keys(Status) as Array<keyof typeof Status>;

        // @ts-ignore
        const currentIndex = statusKeys.indexOf(status);

        if (currentIndex >= 0 && currentIndex < statusKeys.length - 1) {
            const nextKey = statusKeys[currentIndex + 1];
            return Status[nextKey];
        }

        return undefined;
    }

    // 주문 상태 업데이트 함수
    const handleStatusUpdate = useCallback(async (index: number) => {

        try {
            return await dispatch(updateOrder(index)).unwrap();
        } catch (e) {
            console.log('error message : ', e)
            alert('일시적인 문제가 발생했습니다. 다시 시도해주세요.')
        }
    }, [dispatch]);


    return {
        nextStatus,
        handleStatusUpdate
    }
}