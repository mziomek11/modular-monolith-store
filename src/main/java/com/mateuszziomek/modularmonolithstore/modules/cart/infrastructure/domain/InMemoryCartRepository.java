package com.mateuszziomek.modularmonolithstore.modules.cart.infrastructure.domain;

import com.google.common.base.Preconditions;
import com.mateuszziomek.modularmonolithstore.modules.cart.domain.cart.Cart;
import com.mateuszziomek.modularmonolithstore.modules.cart.domain.cart.CartId;
import com.mateuszziomek.modularmonolithstore.modules.cart.domain.cart.CartRepository;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public class InMemoryCartRepository implements CartRepository {
    private final HashMap<CartId, Cart> cartsById = new HashMap<>(10000);

    @Override
    public Mono<Cart> save(Cart cart) {
        Preconditions.checkNotNull(cart, "Cart can't be null");

        cartsById.put(cart.id(), cart);

        return Mono.just(cart);
    }
}