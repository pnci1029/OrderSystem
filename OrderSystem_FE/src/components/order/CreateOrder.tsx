import style from "../../style/order/order.module.scss";
import {CommonInput} from "./CommonInput";
import {OrderTypes} from "../../types/common";
import {useCallback, useState} from "react";
import {useDispatch} from "react-redux";
import {createOrder} from "../../slice/orderSlice";
import {useOrderValidator} from "./hooks/useOrderValidator";
import {useNavigate} from "react-router-dom";


export function CreateOrder() {
    const dispatch = useDispatch<any>();
    const navigate = useNavigate()
    const [menu, setMenu] = useState('');
    const [quantity, setQuantity] = useState(0);

    const [menuErrorMessage, setMenuErrorMessage] = useState('');
    const [quantityErrorMessage, setQuantityErrorMessage] = useState('');

    const {validate} = useOrderValidator({menu, quantity, setMenuErrorMessage, setQuantityErrorMessage});

    // 주문 생성 API 요청
    const handleCreateOrder = useCallback(async () => {

        const result = validate();
        console.log(result)

        if (!result) return false;

        try {
            const result: any = await dispatch(createOrder({name: menu, quantity: quantity}))
                .unwrap()
                .then(() => {
                    alert('주문이 접수되었습니다.');
                    navigate('/order')
                });

            return result;
        } catch (e) {
            console.log('error message : ', e)
            alert('일시적인 문제가 발생했습니다. 다시 시도해주세요.')
        }
        // eslint-disable-next-line
    }, [menu, quantity])

    return (
        <>
            {/* 주문 헤더 영역 */}
            <div className={style.orderTitleWrapper}>
                <span className={style.orderTitle}>
                    무엇을 주문 하시겠습니까
                </span>
            </div>

            {/* 주문 폼 영역 */}
            <div className={style.orderBodyWrapper}>
                <CommonInput type={OrderTypes.MENU} value={menu} setValue={setMenu}
                             errorMessage={menuErrorMessage}/>
                <CommonInput type={OrderTypes.QUANTITY} value={quantity} setValue={setQuantity}
                             errorMessage={quantityErrorMessage}/>
            </div>

            {/* 주문 버튼 영역 */}
            <div className={style.buttonsWrapper}>
                <button className={style.createButton} onClick={handleCreateOrder}>주문</button>
            </div>
        </>
    )
}