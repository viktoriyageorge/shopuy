package com.soa.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.soa.shop.model.ProductDto;

@Service
public class ProductService {

    public static List<ProductDto> products = new ArrayList<>();

    static {
        products.add(ProductDto.builder()
                .productId(1)
                .productName("Serum")
                .photo("assets/img/portfolio/portfolio-1.jpg")
                .price(59.99)
                .quantity(5)
                .description(
                        "Autem ipsum nam porro corporis rerum. Quis eos dolorem eos itaque inventore commodi labore\n"
                                + "                            quia quia. Exercitationem repudiandae officiis neque "
                                + "suscipit non officia eaque itaque enim.")
                .build());

        products.add(ProductDto.builder()
                .productId(2)
                .productName("Natural Juice")
                .photo("assets/img/portfolio/portfolio-2.jpg")
                .price(3.99)
                .quantity(500)
                .description(
                        "Autem ipsum nam porro corporis rerum. Quis eos dolorem eos itaque inventore commodi labore\n"
                                + "                            quia quia. Exercitationem repudiandae officiis neque "
                                + "suscipit non officia eaque itaque enim.")
                .build());

        products.add(ProductDto.builder()
                .productId(3)
                .productName("Tube Mockup")
                .photo("assets/img/portfolio/portfolio-3.jpg")
                .price(23.99)
                .quantity(0)
                .description(
                        "Autem ipsum nam porro corporis rerum. Quis eos dolorem eos itaque inventore commodi labore\n"
                                + "                            quia quia. Exercitationem repudiandae officiis neque "
                                + "suscipit non officia eaque itaque enim.")
                .build());

        products.add(ProductDto.builder()
                .productId(4)
                .productName("COCOOIL")
                .photo("assets/img/portfolio/portfolio-6.jpg")
                .price(33.99)
                .quantity(10)
                .description(
                        "Autem ipsum nam porro corporis rerum. Quis eos dolorem eos itaque inventore commodi labore\n"
                                + "                            quia quia. Exercitationem repudiandae officiis neque "
                                + "suscipit non officia eaque itaque enim.")
                .build());

        products.add(ProductDto.builder()
                .productId(5)
                .productName("Aerin Parfume")
                .photo("assets/img/portfolio/portfolio-5.jpg")
                .price(129.99)
                .quantity(1000)
                .description(
                        "Autem ipsum nam porro corporis rerum. Quis eos dolorem eos itaque inventore commodi labore\n"
                                + "                            quia quia. Exercitationem repudiandae officiis neque "
                                + "suscipit non officia eaque itaque enim.")
                .build());

        products.add(ProductDto.builder()
                .productId(6)
                .productName("Cosmetic BRAND NAME")
                .photo("assets/img/portfolio/portfolio-4.jpg")
                .price(29.99)
                .quantity(0)
                .description(
                        "Autem ipsum nam porro corporis rerum. Quis eos dolorem eos itaque inventore commodi labore\n"
                                + "                            quia quia. Exercitationem repudiandae officiis neque "
                                + "suscipit non officia eaque itaque enim.")
                .build());
    }

    public List<ProductDto> getProducts(List<Integer> productIds) {
        return products.stream().filter(p -> productIds.contains(p.getProductId())).collect(Collectors.toList());
    }

    public ProductDto getProductById(Integer productId) {
        return products.stream().filter(p -> p.getProductId() == productId).findAny().orElse(null);
    }
}
