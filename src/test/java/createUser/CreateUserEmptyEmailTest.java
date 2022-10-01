package createUser;


import data.user.User;
import data.user.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class CreateUserEmptyEmailTest {
    private UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка регистрации без почты")
    @Description("Неуспешное регистрация пользователя без заполненной почтв")
    public void createUserEmptyEmailTest() {
        String message = "Создать пользователя неудалось";
        String expected = "Email, password and name are required fields";
        User user = User.getRandomWithoutEmail("");
        String actual = userClient.createBadUser(user);
        assertEquals(message, expected, actual);
    }

}
