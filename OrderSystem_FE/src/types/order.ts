export interface OrderCreateRequestDto{
    name: string;
    quantity: number;
}

export interface Order{
    id: number;
    name: string;
    status: Status;
    quantity: number;
}

export enum Status{
    RECEIVED="접수됨",
    PROCESSING="처리 중",
    FINISHED="처리 완료"
}