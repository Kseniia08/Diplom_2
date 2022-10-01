package changeUserData;

import com.google.gson.JsonObject;
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


public class ChangeUserDataWithoutAuthTest {
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
    @DisplayName("Неуспешное изменение данных пользователя")
    @Description("Неуспешное изменение данных пользователя без авторизации пользователя")
    public void changeUserDataWithoutAuthTest() {
        String message = "При попытке изменения почты неавторизованного пользователя получен некорректный ответ";
        String expectedMessage = "You should be authorised";
        User user = User.getRandom();
        var jsonWithNewEmail = new JsonObject();
        jsonWithNewEmail.addProperty("email", RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yandex.ru");
        token = userClient.create(user);
        String expectedOldEmail = user.getEmail();
        UserCredentials creds = UserCredentials.from(user);
        userClient.logout(token);
        String actualMessage = userClient.changeUserRejection(jsonWithNewEmail.toString());
        token = userClient.login(creds);
        assertEquals(message, expectedMessage, actualMessage);
        assertEquals(message, expectedOldEmail, userClient.getUserDataEmail(token));
    }


}
