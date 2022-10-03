package change_user_data;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ChangePasswordUserDataWithAuthTest {

    private UserClient userClient;
    private UserClient userClient_delete;
    private Token token;

    @Before
    public void setup() {
        userClient = new UserClient();
        userClient_delete = new UserClient();
    }

    @After
    public void teardown() {
        userClient_delete.delete(token);
    }

    @Test
    @DisplayName("Изменение данных пользователя (изменение пароля")
    @Description("Успешное изменение данных пользователя (пароль) под авторизацией пользователя")
    public void changePasswordUserDataWithAuthTest() {
        String message = "Изменить пароль не удалось";
        User user = User.getRandom();
        String newPassword = RandomStringUtils.randomAlphabetic(10);
        token = userClient.create(user);
        UserCredentials creds = UserCredentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        userClient.changeUserPassword(token, newPassword);
        creds.setPassword(newPassword);
        userClient.logout(token);
        token = userClient.login(creds);
        assertNotNull(message, token.getAccessToken());
        assertNotNull(message, token.getRefreshToken());
    }
}
