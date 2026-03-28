package com.bookstore.orders.purchases;

import java.util.List;

public interface IPurchasesService {
    List<PurchaseDto> getPurchasesByUserId();
    Boolean returnPurchase(Purchase purchase);
}
