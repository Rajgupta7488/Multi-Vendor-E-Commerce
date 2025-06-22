package ecommerce.service.impl;

import ecommerce.dto.AddToCartRequest;
import ecommerce.dto.CartResponse;
import ecommerce.entity.Cart;
import ecommerce.entity.CartItem;
import ecommerce.entity.Product;
import ecommerce.entity.User;
import ecommerce.exception.CartNotFoundException;
import ecommerce.exception.InsufficientStockException;
import ecommerce.repository.CartItemRepository;
import ecommerce.repository.CartRepository;
import ecommerce.service.CartService;
import ecommerce.service.ProductService;
import ecommerce.service.UserService;
import ecommerce.util.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartMapper cartMapper;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUserEntity(userId));
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUserEntity(userId));

        Product product = productService.findProductEntityById(request.getProductId());
        
        if (!productService.isProductAvailable(request.getProductId(), request.getQuantity())) {
            throw new InsufficientStockException(
                "Product is not available or insufficient stock. Product: " + product.getName()
            );
        }

        Optional<CartItem> existingCartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductId());

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            Integer newQuantity = cartItem.getQuantity() + request.getQuantity();
            
            if (!productService.isProductAvailable(request.getProductId(), newQuantity)) {
                throw new InsufficientStockException(
                    "Insufficient stock for the requested quantity. Available: " + product.getStockQuantity()
                );
            }
            
            cartItem.setQuantity(newQuantity);
            cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        calculateCartTotal(cart.getId());
        cart = cartRepository.findById(cart.getId()).get();
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new CartNotFoundException("Item not found in cart"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            if (!productService.isProductAvailable(productId, quantity)) {
                throw new InsufficientStockException("Insufficient stock for the requested quantity");
            }
            
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
            cartItemRepository.save(cartItem);
        }

        calculateCartTotal(cart.getId());
        cart = cartRepository.findById(cart.getId()).get();
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse removeFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        calculateCartTotal(cart.getId());
        
        cart = cartRepository.findById(cart.getId()).get();
        return cartMapper.toResponse(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        cartItemRepository.deleteByCartId(cart.getId());
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public CartResponse createCartForUser(Long userId) {
        Cart cart = createCartForUserEntity(userId);
        return cartMapper.toResponse(cart);
    }

    @Override
    public void calculateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        BigDecimal total = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
        cartRepository.save(cart);
    }

    private Cart createCartForUserEntity(Long userId) {
        User user = userService.findUserEntityById(userId);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }
}