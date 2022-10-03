package login_user;

import data.user.Token;
import data.user.User;
import data.user.UserClient;
import data.user.UserCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertNotNull;

public class LoginUserPositiveTest {
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
    @DisplayName("Авторизация пользователя")
    @Description("Проверка успешной авторизации учетной записи")
    public void loginUserPositiveTest() {
        String message = "Авторизация успешна";
        User user = User.getRandom();
        token = userClient.create(user);
        UserCredentials creds = UserCredentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        assertNotNull(message, token.getAccessToken());
        assertNotNull(message, token.getRefreshToken());
    }

}
