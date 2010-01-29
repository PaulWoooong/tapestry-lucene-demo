package com.samtech.common.domain;


public enum Order {
    ASC, DESC;

    public String toParam() {
        switch (this) {
        case ASC:
            return "asc";
        case DESC:
            return "desc";
        default:
            return "none";
        }
    }

    public static Order valueOfParam(String param) {
        for (Order order : Order.values()) {
            if (order.toParam().equals(param)) {
                return order;
            }
        }

        return null;
    }
}