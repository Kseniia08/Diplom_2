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


public class ChangeEmailUserDataWithAuthTest {

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
    @DisplayName("Изменение данных пользователя (изменение почты)")
    @Description("Успешное изменение данных пользователя (адрес почты) под авторизацией пользователя")
    public void changeEmailUserDataWithAuthTest() {
        String message = "Изменить почту пользователя неудалось";
        User user = User.getRandom();
        String expectedNewEmail = (RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru");
        token = userClient.create(user);
        UserCredentials creds = UserCredentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        String actualNewEmail = userClient.changeUserEmail(token, expectedNewEmail);
        assertEquals(message, expectedNewEmail, actualNewEmail);
    }
}
