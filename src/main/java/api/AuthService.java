package api;

import io.restassured.filter.log.LogDetail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

//--------------------------
//Синглтон-класс авторизации
//--------------------------
public class AuthService {
    private Map<String, List<String>> authInfo = new HashMap<>();   //HashMap<login, <password, token>>
    private final static String PROPERTIES_FILE_PATH = "src/main/resources/API_x_client.properties";
    private String basePathString = "";


    private AuthService() {
        basePathString = getProperties(PROPERTIES_FILE_PATH).getProperty("baseURI");
    }

    public String logIn(String login, String password) {
        if (login == null || password == null) return "";
        if (authInfo.containsKey(login) && authInfo.get(login).get(0).equals(password))
            return authInfo.get(login).get(1);
        String token =
                given()
                        .baseUri(basePathString + "/auth/login")
                        .log().ifValidationFails(LogDetail.ALL)   //Логирование при ошибке
                        .contentType("application/json; charset=utf-8")
                        .body("{\"username\": \"" + login + "\", \"password\": \"" + password + "\"}")
                        .when()
                        .post()
                        .then()
                        .log().ifValidationFails()
                        .statusCode(201)             //Проверка статус-кода
                        .contentType("application/json; charset=utf-8")     //Проверка content-type
                        .extract().path("userToken").toString();

        if (!token.equals("")) {
            if (authInfo.containsKey(login)) {
                authInfo.replace(login, List.of(password, token));   //Если пользователь уже получал токен, то заменяем
            } else {
                authInfo.put(login, List.of(password, token));
            }
        }
        return token;
    }

    public void logOut(String login) {
        authInfo.entrySet().removeIf(entry -> entry.getKey().contains(login));
    }

    public static class SingletonHolder {
        public static final AuthService HOLDER_INSTANCE = new AuthService();
    }

    public static AuthService getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    //Получить параметры из файла
    public Properties getProperties(String path) {
        File propFile = new File(path);
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}