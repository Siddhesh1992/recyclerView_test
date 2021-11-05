package com.example.recyclerview_test.models;

import java.util.*;

public class Plans {
    int id = 0;

    public boolean isCovered() {
        return isCovered;
    }

    public int getProduct_feature_id() {
        return product_feature_id;
    }

    int product_feature_id = 0;
    String product_feature_name = null;

    public ArrayList<ProductDetails> getProduct_detail() {
        return product_detail;
    }

    ArrayList<ProductDetails> product_detail = null;
    boolean isCovered = false;

    public void setCovered(boolean covered) {
        isCovered = covered;
    }
//    int product_type = 0;
//    String content = null;
//    int order = 0;
//    int is_mandantory = 0;
//    static HashSet CoveredPlans = new HashSet();

    public String getProduct_feature_name() {
        return product_feature_name;
    }

    @Override
    public String toString() {
        return "Plans{" +
                "id=" + id +
                ", product_feature_id=" + product_feature_id +
                ", product_feature_name='" + product_feature_name + '\'' +
                ", product_detail=" + product_detail +
                '}';
    }

    public Plans(int id, int product_feature_id, String product_feature_name, ArrayList<ProductDetails> product_detail) {
        this.id = id;
        this.product_feature_id = product_feature_id;
        this.product_feature_name = product_feature_name;
        this.product_detail = product_detail;
    }
}
