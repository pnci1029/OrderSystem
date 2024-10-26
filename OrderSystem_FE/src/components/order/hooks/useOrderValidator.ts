import {useCallback} from "react";

interface Args{
    menu: string;
    quantity: number;
    setMenuErrorMessage: (_: string) => void;
    setQuantityErrorMessage: (_: string) => void;
}

export function useOrderValidator({menu, quantity, setMenuErrorMessage, setQuantityErrorMessage}:Args) {

    const validate = useCallback(() =>{
        let isValid = true;
        setMenuErrorMessage('')
        setQuantityErrorMessage('')

        if (menu.length < 1) {
            setMenuErrorMessage('메뉴를 입력하세요');
            isValid = false;
        }

        if (quantity < 1) {
            setQuantityErrorMessage('수량을 입력하세요');
            isValid = false;
        }


        return isValid;

    // eslint-disable-next-line
    },[menu, quantity])
    return {validate}
}