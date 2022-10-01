package changeUserData;

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

public class ChangeLoginUserDataWithAuthTest {

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
    @DisplayName("Изменение данных пользователя (изменение логина)")
    @Description("Успешное изменение данных пользователя (логин) под авторизацией пользователя")
    public void changeLoginUserDataWithAuthTest() {
        String message = "Изменить логин пользователя не удалось";
        User user = User.getRandom();
        String expectedNewName = RandomStringUtils.randomAlphabetic(10);
        token = userClient.create(user);
        UserCredentials creds = UserCredentials.from(user);
        userClient.logout(token);
        token = userClient.login(creds);
        String actualNewName = userClient.changeUserName(token, expectedNewName);
        assertEquals(message, expectedNewName, actualNewName);
    }
}
