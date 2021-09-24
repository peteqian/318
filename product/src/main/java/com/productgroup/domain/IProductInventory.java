package com.productgroup.domain;

import java.util.Map;

public interface IProductInventory {
    Map<Double, String> checkInventory(String productName, long quantity);
}
