import style from "../../style/common/header.module.scss"
import {useOrderGetter} from "./hooks/useOrderGetter";

export function Main() {

    const categories = [
        {label: '식사 메뉴', page: 'food',},
        {label: '추가 메뉴', page: 'additional-menu'},
        {label: '음료', page: 'beverage'},
    ];

    const {getMenu} = useOrderGetter();
    const getMennn = () =>{
        const result  = getMenu();
    }
    return (
        <>
            <header className={style.header}>
                <span className={style.homeMenu}>홈으로 가기</span>
                <div className={style.categoryWrapper}>
                    {categories.map((values, idx) => (
                        <button className={style.category} onClick={getMennn}>
                            {values.label}
                        </button>
                    ))}
                </div>
            </header>
        </>
    );
}