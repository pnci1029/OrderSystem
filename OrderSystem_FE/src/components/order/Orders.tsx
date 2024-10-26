import {useEffect, useState} from "react";
import {Order, Status} from "../../types/order";
import style from "../../style/order/order.module.scss"
import {useOrderFunctions} from "./hooks/useOrderFunctions";

export function Orders() {
    const [orders, setOrders] = useState<Order[]>([]);
    const {nextStatus, handleStatusUpdate} = useOrderFunctions();

    useEffect(() => {
        const socket = new WebSocket('ws://localhost:8080/ws/orders');

        socket.addEventListener('open', (event) => {
            socket.send('websocket is Open')
        })

        socket.addEventListener('message', (event) => {
            console.log(event.data)
        })

        socket.onmessage = (event) => {
            const newOrders: Order[] = JSON.parse(event.data);
            setOrders((newOrders));
        };

        return () => socket.close();
    }, []);

    return (
        <>
            <div className={style.orderTitleWrapper}>
                <span className={style.orderTitle}>
                    실시간 주문 현황
                </span>
            </div>

            <div className={style.ordersWrapper}>
                {orders.map((data, index) => (
                    <div className={style.orderItems} key={index}>
                        <p className={style.text}>
                            {Status[data.status as unknown as keyof typeof Status]}
                        </p>
                        <p className={style.text}>
                            주문 메뉴 이름 : {data.name}
                        </p>
                        <p className={style.text}>
                            주문 수량 : {data.quantity}
                        </p>

                        {Status[data.status as unknown as keyof typeof Status] !== Status.FINISHED && (
                            <button className={style.statusUpdateButton}
                                    onClick={() => handleStatusUpdate(index)}>
                                {nextStatus(data.status)}
                            </button>
                        )}
                    </div>
                ))}
            </div>
        </>
    );
}