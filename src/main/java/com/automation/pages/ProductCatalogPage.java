package com.automation.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Product catalog — the screen the app lands on after a successful login
 * (and also the logged-out landing screen; this app has no login wall).
 * Verified against fragment_product_catalog.xml.
 */
public class ProductCatalogPage extends BasePage {

    @AndroidFindBy(id = "productTV")
    private WebElement productsTitle;

    @AndroidFindBy(id = "productRV")
    private WebElement productList;

    public boolean isDisplayed() {
        return isDisplayed(productsTitle) && isDisplayed(productList);
    }
}
