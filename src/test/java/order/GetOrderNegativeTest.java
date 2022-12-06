package order;

import data.order.Ingredients;
import data.order.Order;
import data.order.OrderClient;
import data.user.Token;
import data.user.User;
import data.user.UserClient;
import data.user.UserCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetOrderNegativeTest {
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
    @DisplayName("Получение заказов без авторизации")
    @Description("Проверка получение заказов без авторизации")
    public void getOrderNegativeTest() {
        String message = "Получение заказов неуспешно";
        String expectedMessage = "You should be authorised";
        OrderClient orderClient = new OrderClient();
        OrderClient orderClientNew = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(5));
        User user = User.getRandom();
        token = userClient.create(user);
        UserCredentials creds = UserCredentials.from(user);
        Order order = orderClient.createOrderWithAuthUser(token, ingredients);
        userClient.logout(token);
        String actualMessage = orderClientNew.getOrderForNotAuthUser();
        token = userClient.login(creds);
        assertEquals(message, actualMessage, expectedMessage);
    }
}

