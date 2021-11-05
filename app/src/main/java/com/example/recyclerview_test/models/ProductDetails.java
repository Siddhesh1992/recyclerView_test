package com.example.recyclerview_test.models;

public class ProductDetails {
    int id = 0;
    int plan_feature_mapping_id = 0;
    int premium = 0;

    public int getPremium() {
        return premium;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "id=" + id +
                ", plan_feature_mapping_id=" + plan_feature_mapping_id +
                ", premium=" + premium +
                '}';
    }

    //    String name = null;
//    int is_wavied_off = 0;
//    int sum_insured = 0;
//    int sum_insured_type = 0;
//    int deductible_from = 0;
//    int premium_type = 1;
//    int duration_type = 0;
//    int duration_value = 0;
//    int duration_unit = 0;

    public ProductDetails(int id, int plan_feature_mapping_id, int premium) {
        this.id = id;
        this.plan_feature_mapping_id = plan_feature_mapping_id;
        this.premium = premium;
    }
}
