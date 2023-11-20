package ru.mrnightfury.pr7.db.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mrnightfury.pr7.db.model.CartItem;

import java.util.ArrayList;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	@Query("SELECT ci FROM Cart c JOIN c.items ci WHERE c.id = :cartId")
	ArrayList<CartItem> findCartItemsByCartId(@Param("cartId") Long cartId);

	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
	void deleteAllByCartId(@Param("cartId") Long cartId);
}