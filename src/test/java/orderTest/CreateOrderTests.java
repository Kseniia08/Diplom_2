package orderTest;

import data.order.Ingredients;
import data.order.Order;
import data.order.OrderClient;
import data.user.Token;
import data.user.User;
import data.user.UserClient;
import data.user.UserCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertTrue;

public class CreateOrderTests {
    private UserClient userClient;
    private Token token;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @After
    public void teardown() {
         userClient.delete(token);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа под авторизацией и с перечнем инградииентов")
    public void createOrderWithAuthTest() {
        String message = "Заказ создан";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(5));
        User user = User.getRandom();
        token = userClient.create(user);
        Order order = orderClient.createOrderWithAuthUser(token, ingredients);
        assertEquals(message, order.getIngredients().size(), ingredients.getIngredients().size());
        for (int i = 0; i < order.getIngredients().size() - 1; i++) {
            assertEquals(message, order.getIngredients().get(i).get_id(), ingredients.getIngredients().get(i));
        }
        assertNotNull(message, order.get_id());
        assertEquals(message, user.getName(), order.getOwner().getName());
        assertEquals(message, user.getEmail(), order.getOwner().getEmail());
        assertNotNull(message, order.getStatus());
        assertTrue(message, order.getNumber() > 0);
        assertTrue(message, order.getPrice() > 0);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа без авторизацией и с без инградииентов")
    public void createOrderWithoutAuthAndWithoutIngredientsTest() {
        String message = "Заказ не создан";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(3));
        User user = User.getRandom();
        token = userClient.create(user);
        UserCredentials creds = UserCredentials.from(user);
        userClient.logout(token);
        Order order = orderClient.createOrderWihtoutAuthUser(ingredients);
        token = userClient.login(creds);
        assertNull(message, order.getIngredients());
        assertNull(message, order.get_id());
        assertNull(message, order.getOwner());
        assertNull(message, order.getStatus());
        assertNull(message, order.getName());
        assertTrue(message, order.getNumber() > 0);
        assertEquals(message, order.getPrice(), 0);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с авторизацией и с без инградииентов")
    public void createOrderWithAuthAndWithoutIngredientsTest() {
        String message = "Заказ не создан";
        String expected_message = "Ingredient ids must be provided";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(3));
        User user = User.getRandom();
        token = userClient.create(user);
        String actual_message = orderClient.createOrderWithoutIngredients(token);
        assertEquals(message, expected_message, actual_message);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с авторизацией и с некорректным хешом инградиентов")
    public void createOrderWithAuthAndWithWrongHashIngredientsTest() {
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(List.of(RandomStringUtils.randomAlphabetic(10)));
        User user = User.getRandom();
        token = userClient.create(user);
        orderClient.createOrderWitBadHashIngredients(token, ingredients);
    }
}
