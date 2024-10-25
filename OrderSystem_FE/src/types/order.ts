export interface OrderCreateRequestDto{
    name: string;
    quantity: number;
}

export interface Order{
    id: number;
    name: string;
    quantity: number;
}