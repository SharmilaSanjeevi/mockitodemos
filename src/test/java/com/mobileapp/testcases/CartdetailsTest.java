package com.mobileapp.testcases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mobileapp.exception.EmptyCartException;
import com.mobileapp.exception.MobileNotFoundException;
import com.mobileapp.model.Mobile;
import com.mobileapp.service.CartDetails;
import com.mobileapp.service.ICartService;
import com.mobileapp.service.OrderDetails;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class CartdetailsTest {

	@Mock
	ICartService cartService;

	@InjectMocks
	CartDetails cartDetails;
	Mobile mobile1, mobile2, mobile3, mobile4, mobile5, mobile6;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

		mobile1 = new Mobile(1, "Samsung", "A53", 26000);
		mobile2 = new Mobile(2, "Xiaomi", "A3", 16000);
		mobile3 = new Mobile(3, "Realme", "Neo2", 32000);
		mobile4 = new Mobile(4, "Xiaomi", "3s", 10000);
		mobile5 = new Mobile(5, "Xiaomi", "4a", 10000);
		mobile6 = new Mobile(6, "Samsung", "S21", 53000);
		cartDetails = new CartDetails();
		cartDetails.setCartService(cartService);
		List<Mobile> mobileData = Arrays.asList(mobile1, mobile2, mobile3, mobile4, mobile5, mobile6);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("AddCart")
	void testAddcart() throws MobileNotFoundException {
		doNothing().when(cartService).addtoCart(mobile1);
		String actual = cartDetails.addtoCart(mobile1);
		String expected = "added Successfully";
		assertEquals(expected, actual, "Invalid");
	}

	@Test
	@DisplayName("checking addCart with exception")
	void testAddCartException() throws MobileNotFoundException {
		doThrow(new MobileNotFoundException("invalid")).when(cartService).addtoCart(mobile1);
		assertThrows(MobileNotFoundException.class, () -> cartDetails.addtoCart(mobile1));
	}

	@Test
	@DisplayName("showCart")
	void testShowCart() throws MobileNotFoundException, EmptyCartException {
		List<Mobile> expected = Arrays.asList(mobile1, mobile3, mobile6);
		doReturn(Arrays.asList(mobile1, mobile6, mobile3)).when(cartService).showCart();
		List<Mobile> actualMobiles = cartDetails.showCart();
		assertEquals(expected, actualMobiles, "not equal");
	}

	@Test
	@DisplayName("checking showCart is null")
	void testShowCartNull() throws MobileNotFoundException, EmptyCartException {

		doReturn(null).when(cartService).showCart();
		assertNull(cartDetails.showCart());
	}

	@Test
	@DisplayName("checking showcart is empty")
	void testShowCartEmpty() throws MobileNotFoundException, EmptyCartException {
		doThrow(new EmptyCartException()).when(cartService).showCart();
		assertThrows(EmptyCartException.class, () -> cartDetails.showCart());
	}

	@Test
	@DisplayName("checking to remove cart")
	void testRemoveCart() throws EmptyCartException, MobileNotFoundException {
		doReturn(true).when(cartService).removeFromCart(mobile1);
		assertEquals(true, cartDetails.removeFromCart(mobile1));
	}

	@Test
	@DisplayName("checking to removecart is empty")
	void testRemoveCartEmpty() throws MobileNotFoundException {
		doThrow(new MobileNotFoundException()).when(cartService).removeFromCart(mobile3);
		assertThrows(MobileNotFoundException.class, () -> cartDetails.removeFromCart(mobile3));
	}
}
