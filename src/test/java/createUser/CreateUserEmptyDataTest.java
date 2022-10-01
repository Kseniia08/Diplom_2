package createUser;

import data.user.User;
import data.user.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CreateUserEmptyDataTest {
    private UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка регистрации с невалидными данными")
    @Description("Неуспешное регистрация пользователя с пустыми обязательными параметрами")
    public void createUserEmptyDataTest() {
        String message = "Создать пользователя неудалось";
        String expected = "Email, password and name are required fields";
        User user = User.getEmpty();
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }
}
