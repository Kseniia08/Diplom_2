package create_user;

import data.user.Token;
import data.user.User;
import data.user.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateDoubleUserTests {
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
    @DisplayName("Проверка регистрации дубликата пользователя")
    @Description("Неуспешное создание дубля пользователя")
    public void createDoubleUserTests() {
        String message = "Создание дубля пользователя невозможно";
        String expected = "User already exists";
        User user = User.getRandom();
        token = userClient.create(user);
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }
}
