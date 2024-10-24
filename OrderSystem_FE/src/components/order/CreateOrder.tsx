import style from "../../style/order/order.module.scss";
import {CommonInput} from "./CommonInput";
import {OrderTypes} from "../../types/common";
import {useCallback, useState} from "react";


export function CreateOrder() {
    const [menu, setMenu] = useState('');
    const [quantity, setQuantity] = useState(0);


    // 주문 생성 API 요청
    const handleCreateOrder = useCallback(() =>{
        console.log(menu)
        console.log(quantity)
    },[menu, quantity])
    return(
        <>
            {/* 주문 헤더 영역 */}
            <div className={style.orderTitleWrapper}>
                <span className={style.orderTitle}>
                    무엇을 주문 하시겠습니까
                </span>
            </div>

            {/* 주문 폼 영역 */}
            <div className={style.orderBodyWrapper}>
                <CommonInput type={OrderTypes.MENU} value={menu} setValue={setMenu}/>
                <CommonInput type={OrderTypes.QUANTITY} value={quantity} setValue={setQuantity}/>
            </div>

            {/* 주문 버튼 영역 */}
            <div className={style.buttonsWrapper}>
                <button className={style.createButton} onClick={handleCreateOrder}>등록하기</button>
            </div>
        </>
    )
}