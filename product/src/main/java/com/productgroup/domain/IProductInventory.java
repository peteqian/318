package com.productgroup.domain;

import java.util.Map;

public interface IProductInventory {
    Map<String, String> checkInventory(String productName, long quantity);
}
