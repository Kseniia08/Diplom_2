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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

public class GetOrderPositiveTest {
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
    @DisplayName("Получение заказов с авторизацией")
    @Description("Проверка получение заказов с авторизацией пользователя")
    public void getOrderPositiveTest() {
        String message = "Получение заказов произошло успешно";
        OrderClient orderClient = new OrderClient();
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredients(orderClient.getListIngredients(5));
        User user = User.getRandom();
        token = userClient.create(user);
        UserCredentials creds = UserCredentials.from(user);
        Order order0 = orderClient.createOrderWithAuthUser(token, ingredients);
        List<Integer> actualNumberOrders = orderClient.getOrderForAuthUser(token);
        assertEquals(message, actualNumberOrders.size(), 3);
        assertEquals(message, order0.getNumber(), actualNumberOrders.get(0).intValue());
    }
}

