package ecommerce.util;

import ecommerce.dto.CartItemResponse;
import ecommerce.dto.CartResponse;
import ecommerce.entity.Cart;
import ecommerce.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setUserId(cart.getUser().getId());
        response.setTotalAmount(cart.getTotalAmount());
        
        List<CartItemResponse> itemResponses = cart.getCartItems().stream()
                .map(this::toCartItemResponse)
                .collect(Collectors.toList());
        
        response.setItems(itemResponses);
        response.setTotalItems(itemResponses.size());
        
        return response;
    }

    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setProductImage(cartItem.getProduct().getImageUrl());
        response.setQuantity(cartItem.getQuantity());
        response.setUnitPrice(cartItem.getUnitPrice());
        response.setTotalPrice(cartItem.getTotalPrice());
        response.setAvailableStock(cartItem.getProduct().getStockQuantity());
        
        return response;
    }
}