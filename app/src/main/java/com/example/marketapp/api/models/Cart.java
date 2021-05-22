package com.example.marketapp.api.models;

public class Cart
{
    public Item item;
    public int quantity;
    public double extendedPrice = 0.0;

    public Cart(Item item, int quantity)
    {
        this.item = item;
        this.quantity = quantity;
        this.extendedPrice = item.unitPrice * quantity;
    }
}
