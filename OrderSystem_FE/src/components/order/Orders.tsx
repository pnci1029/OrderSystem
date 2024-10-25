import {useEffect, useState} from "react";

export function Orders() {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        const socket = new WebSocket('ws://localhost:8080/ws/orders');

        socket.addEventListener('open',(event) =>{
            socket.send('hellowwwww')
        })

        socket.addEventListener('message',(event) =>{
            console.log(event.data)
        })

        socket.onmessage = (event) => {
            const newOrder = event.data;
            console.log(newOrder)
            // setOrders((prevOrders) => [...prevOrders, newOrder]);
        };

        return () => socket.close();
    }, []);
    return(
        <>
            하이
        </>
    )
}