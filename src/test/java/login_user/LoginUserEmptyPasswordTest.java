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
import static org.junit.Assert.assertEquals;

public class LoginUserEmptyPasswordTest {
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
    @DisplayName("Авторизация пользователя с невалидным паролем")
    @Description("Проверка авторизации учетной записи с пустым паролем")
    public void loginUserEmptyPasswordTest() {
        String message = "Авторизация неуспешна";
        String expected = "email or password are incorrect";
        User user = User.getRandom();
        token = userClient.create(user);
        userClient.logout(token);
        UserCredentials creds = UserCredentials.from(user);
        creds.UserCredentialsBadPassword(creds);
        String actual = userClient.loginWithBadParams(creds);
        assertEquals(message, expected, actual);
    }
}
