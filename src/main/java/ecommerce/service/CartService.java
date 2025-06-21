package ecommerce.service;

import ecommerce.dto.AddToCartRequest;
import ecommerce.dto.CartResponse;

public interface CartService {
    CartResponse getCartByUserId(Long userId);
    CartResponse addToCart(Long userId, AddToCartRequest request);
    CartResponse updateCartItem(Long userId, Long productId, Integer quantity);
    CartResponse removeFromCart(Long userId, Long productId);
    void clearCart(Long userId);
    CartResponse createCartForUser(Long userId);
    void calculateCartTotal(Long cartId);
}