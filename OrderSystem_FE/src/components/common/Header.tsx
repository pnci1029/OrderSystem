import style from "../../style/common/header.module.scss";
import {useNavigate} from "react-router-dom";
import {useOrderGetter} from "./hooks/useOrderGetter";

export function Header() {

    const navigate = useNavigate();
    const categories = [
        {label: '주문 하기', page: '/order/post',},
        {label: '주문 현황', page: '/order'},
    ];

    const {getMenu} = useOrderGetter();

    const handleChangePage = (path: string) => {
        navigate(path)

    };
    return (
        <header className={style.header}>
            <span className={style.homeMenu} onClick={() =>handleChangePage('/')}>홈으로 가기</span>
            <div className={style.categoryWrapper}>
                {categories.map((values, idx) => (
                    <button className={style.category} onClick={() => handleChangePage(values.page)}>
                        {values.label}
                    </button>
                ))}
            </div>
        </header>
    )
}