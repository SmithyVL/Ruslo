# bm-security

___
Модуль интеграции со [**spring-security**](https://docs.spring.io/spring-security/reference/index.html). При подключении привносит дополнительные классы конфигурации 
безопасности приложения.
___

## Полезные ресурсы

1. [**Использование JWT на Kotlin со Spring Security**](https://www.baeldung.com/kotlin/spring-security-jwt);
2. [**Реактивный Spring Security для создания JWT токенов**](https://www.geeksforgeeks.org/reactive-jwt-authentication-using-spring-webflux/);
3. [**Spring Security на Spring Boot 3**](https://blog.codersee.com/spring-boot-3-spring-security-6-with-kotlin-jwt/).

## Подключение

На данный момент это только внутренний модуль проекта, а это значит, что нужно подключить модуль таким образом:

```kotlin
implementation(project(":bm-integration:bm-security"))
```

После подключения нужно создать реализацию интерфейса `ReactiveUserDetailsService`, с помощью которой можно будет 
находить пользователей для аутентификации запросов. **Без этого действия приложение не запустится!**

Затем нужно озаботиться тем, чтобы указать пути, которые должны быть открытыми извне, в свойстве 
`bm-security.permitAllPaths` _(смотри раздел "Параметры конфигурации и переменные окружения")_. **Без этого действия 
приложение запустится, потому что есть стандартные настройки, но они могут быть не актуальны.**

## Параметры конфигурации и переменные окружения
_Переопределять эти свойства в модуле подключения стартера - не обязательно!_
```yaml
bm-security:
  jwt:
    issuer: ${BM_SECURITY_JWT_ISSUER:Blimfy}
    key: ${BM_SECURITY_JWT_KEY:secret_key_is_very_well_and_good}
  permitAllPaths:
    # Ниже указаны примерные значения для путей к Swagger, но они могут быть другими.
    - "/swagger-ui/**"
    - "/v3/api-docs/**"
    # Ниже указаны примерные значения для путей авторизации/аутентификации, но они могут быть другими.
    - "/v1/auth/sign-up"
    - "/v1/auth/sign-in"
```

- **BM_SECURITY_JWT_ISSUER:** имя издателя токена;
- **BM_SECURITY_JWT_KEY:** уникальный ключ, используемый для создания токенов.