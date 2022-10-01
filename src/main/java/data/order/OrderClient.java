package data.order;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data.RestClient;
import data.user.Token;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static constants.Constants.INGREDIENTS_PATH;
import static constants.Constants.ORDERS_PATH;

public class OrderClient extends RestClient {

    public List<String> getListIngredients(int countIngredients) {
        Random rand = new Random();
        List<String> allIdIngredients = reqSpec
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("data._id");
        List<String> ingredientsForBurger = new ArrayList<>();
        for (int i = 0; i < countIngredients; i++) {
            ingredientsForBurger.add(allIdIngredients.get(rand.nextInt(allIdIngredients.size())));
        }
        return ingredientsForBurger;
    }

    public Order createOrderWithAuthUser(Token token, Ingredients ingredientsForBurger) {
        var response = reqSpec
                .header("authorization", token.getAccessToken())
                .body(ingredientsForBurger)
                .when()
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();
        JsonObject element = new Gson().fromJson(response, JsonObject.class).getAsJsonObject("order");
        return new Gson().fromJson(element, Order.class);
    }


    public Order createOrderWihtoutAuthUser(Ingredients ingredientsForBurger) {
        var response = reqSpec
                .body(ingredientsForBurger)
                .when()
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();
        JsonObject element = new Gson().fromJson(response, JsonObject.class).getAsJsonObject("order");
        return new Gson().fromJson(element, Order.class);
    }

    public String createOrderWithoutIngredients(Token token) {
        return reqSpec
                .header("authorization", token.getAccessToken())
                .when()
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    public void createOrderWitBadHashIngredients(Token token, Ingredients ingredientsForBurger) {
        reqSpec
                .header("authorization", token.getAccessToken())
                .body(ingredientsForBurger)
                .when()
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(404);
    }

    public List<Integer> getOrderForAuthUser(Token token) {
        return reqSpec
                .header("authorization", token.getAccessToken())
                .when()
                .get(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("orders.number");
    }

    public String getOrderForNotAuthUser() {
        return reqSpec
                .when()
                .get(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
    }
}
