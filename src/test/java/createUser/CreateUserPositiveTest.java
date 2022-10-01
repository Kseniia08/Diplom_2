package createUser;

import data.user.Token;
import data.user.User;
import data.user.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class CreateUserPositiveTest {

    private static UserClient userClient;
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
    @DisplayName("Создание пользователя")
    @Description("Проверка успешной регистрации нового пользователя")
    public void createUserPositiveTest() {
        String message = "Регистрация пользователя";
        User user = User.getRandom();
        token = userClient.create(user);
        assertNotNull(message, token.getAccessToken());
        assertNotNull(message, token.getRefreshToken());
    }
}
