package com.soa.shop.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soa.shop.model.ProductDto;
import com.soa.shop.model.UserCart;
import com.soa.shop.model.UserProfile;
import com.soa.shop.repository.UserCartRepository;
import com.soa.shop.repository.UserProfileRepository;

@Service
public class UserCartService {

    private final ProductService productService;
    private final UserCartRepository userCartRepository;
    private final UserProfileService userProfileService;
    private final HashMap<String, UserCart> userCartMap = new HashMap<>();

    public UserCartService(@Autowired ProductService productService, @Autowired UserCartRepository userCartRepository,
            @Autowired UserProfileService userProfileService) {
        this.productService = productService;
        this.userCartRepository = userCartRepository;
        this.userProfileService = userProfileService;
    }

    public void save(UserCart user) {
        userCartRepository.save(user);
    }

    public String buyProducts(String email) {
        UserProfile user = userProfileService.getUserByEmail(email);
        double totalPrice = getTotalPrice(email);
        if (this.haveEnoughBalance(user, totalPrice)) {
            if (this.purchase(user, totalPrice)) {
                return "";
            }
            return "Purchase was not successful!";
        } else {
            return "Not enough balance!";
        }
    }

    private boolean haveEnoughBalance(UserProfile user, double totalPrice) {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpGet request = new HttpGet("http://localhost:8088/account/" + user.getBankId() + "?price=" + totalPrice);
            return Boolean.parseBoolean(EntityUtils.toString(client.execute(request).getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return false;
    }

    private boolean purchase(UserProfile user, double totalPrice) {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost(
                    "http://localhost:8088/buyProduct/?amount=" + totalPrice + "&accountId=" + user.getBankId());
            client.execute(request);
            removeAllProductsFromCart(user.getEmail());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }

        return false;
    }

    public List<ProductDto> getProducts(String email) throws UsernameNotFoundException {
        List<Integer> productIds = getUser(email).getProductIds();
        return productService.getProducts(productIds);
    }

    public void addProductToCart(String email, Integer productId) throws UsernameNotFoundException {
        getUser(email);
        userCartMap.get(email).getProductIds().add(productId);
    }

    public void removeProductFromCart(String email, Integer productId) throws UsernameNotFoundException {
        getUser(email);
        userCartMap.get(email).getProductIds().remove(productId);
    }

    public void removeAllProductsFromCart(String email) throws UsernameNotFoundException {
        getUser(email);
        userCartMap.get(email).setProductIds(new ArrayList<>());
    }

    public Double getTotalPrice(String email) throws UsernameNotFoundException {
        List<Integer> productIds = getUser(email).getProductIds();
        return productService.getProducts(productIds).stream().mapToDouble(ProductDto::getPrice).sum();
    }

    private UserCart getUser(String email) {
        userProfileService.getUserByEmail(email);
        userCartMap.computeIfAbsent(email, k -> new UserCart(k, new ArrayList<>()));
        return userCartMap.get(email);

    }
}
