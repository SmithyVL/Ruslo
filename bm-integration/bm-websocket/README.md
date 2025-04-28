# bm-websocket

___
Модуль интеграции с 
[**WebSocket**](https://docs.spring.vmware.com/spring-framework/reference/web/webflux-websocket.html). При подключении 
привносит дополнительные классы конфигурации приложения.
___

## Полезные ресурсы

1. [**Реактивные WebSocket'ы**](https://www.baeldung.com/spring-5-reactive-websockets).

## Подключение

На данный момент это только внутренний модуль проекта, а это значит, что нужно подключить модуль таким образом:

```kotlin
implementation(project(":bm-integration:bm-websocket"))
```

После подключения нужно создать реализацию абстрактного класса `WebSocketStorage`, с помощью которого можно будет 
хранить WebSocket сессии. **Без этого действия приложение не запустится!**