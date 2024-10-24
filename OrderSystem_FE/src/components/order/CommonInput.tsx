import style from "../../style/order/order.module.scss";
import {OrderTypes} from "../../types/common";

interface Args{
    type: OrderTypes;
    value: any;
    setValue: (_: any) => void
}
export function CommonInput({type, value, setValue}:Args) {

    // 라벨명, 인풋 타입 정의
    const renderInputField = () => {
        const inputProps = {
            MENU: {label: '메뉴 이름', type: 'text'},
            QUANTITY: {label: '수량', type: 'number'},
        };

        const {label, type: inputType} = inputProps[type] || {};

        return label ? (
            <div className={style.titleWrapper}>
                <span>{label} : </span>
                <input className={style.inputStyle} type={inputType}
                       value={value}
                       onChange={(e) => setValue(inputType === 'number' ? Number(e.target.value) : e.target.value)}

                />
            </div>
        ) : null;
    };

    return <div>{renderInputField()}</div>;
}