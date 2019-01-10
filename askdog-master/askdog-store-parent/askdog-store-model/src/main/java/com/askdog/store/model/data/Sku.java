package com.askdog.store.model.data;


import java.util.List;

public class Sku extends Target {

    private List<SkuItem> skuItems;

    public List<SkuItem> getSkuItems() {
        return skuItems;
    }

    public void setSkuItems(List<SkuItem> skuItems) {
        this.skuItems = skuItems;
    }

    public static class SkuItem {

        private String attributeName;

        private String attributeValue;

        public SkuItem(String attributeName, String attributeValue) {
            this.attributeName = attributeName;
            this.attributeValue = attributeValue;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getAttributeValue() {
            return attributeValue;
        }

        public void setAttributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
        }
    }
}
