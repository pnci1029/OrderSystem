import style from "../../style/order/order.module.scss";
import {OrderTypes} from "../../types/common";

interface Args{
    type: OrderTypes;
    value: any;
    setValue: (_: any) => void
    errorMessage: string;
}
export function CommonInput({type, value, setValue, errorMessage}:Args) {

    // 라벨명, 인풋 타입 정의
    const renderInputField = () => {
        const inputProps = {
            MENU: {label: '메뉴 이름', type: 'text', errorMessage:errorMessage},
            QUANTITY: {label: '수량', type: 'number',errorMessage:errorMessage},
        };

        const {label, type: inputType} = inputProps[type] || {};

        return label ? (
            <div className={style.titleWrapper}>
                <span>{label} : </span>
                <input className={style.inputStyle} type={inputType}
                       value={value}
                       onChange={(e) => setValue(inputType === 'number' ? Number(e.target.value) : e.target.value)}
                />
                <p className={style.errorMessage}>{errorMessage}</p>
            </div>
        ) : null;
    };

    return <div>{renderInputField()}</div>;
}