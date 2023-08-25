package com.ecureuill.ada.avanade.orderapi.infra.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "dummyjson", value = "dummyjson", url = "https://dummyjson.com")
public interface DummyJsonClient {
    @RequestMapping(method = RequestMethod.GET, value = "/products/search?q=phone")
    ProductList getProducts();
} 
