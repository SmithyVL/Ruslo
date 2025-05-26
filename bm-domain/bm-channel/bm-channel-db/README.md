# bm-channel-db

___
Модуль для работы с сущностями каналов и их сообщений в БД.
___

## Подключение

На данный момент это только внутренний модуль проекта, а это значит, что нужно подключить модуль таким образом:

```kotlin
implementation(project(":bm-domain:bm-channel:bm-channel-db"))
```

**Так же нужно не забыть подключить аннотацию "EnableR2dbcAuditing" в модуль приложения.**

## Параметры конфигурации и переменные окружения
_Переопределять эти свойства в модуле подключения стартера - не обязательно!_
```yaml
bm-channel-db:
  liquibase:
    changelog: classpath:db/changelog/db.changelog-master-channel.yaml

spring:
  # Настройки реактивной БД.
  r2dbc:
    url: r2dbc:postgresql://${BM_DB_HOST:localhost}:${BM_DB_PORT:5432}/blimfy
    username: ${BM_DB_USERNAME:blimfy_admin}
    password: ${BM_DB_PASSWORD:blimfy_password}

  # Настройки версионирования БД.
  liquibase:
    # Выключение автоматического запуска, потому что запуск выполняется с помощью кастомных Spring Liquibase.
    enabled: false
    url: jdbc:postgresql://${BM_DB_HOST:localhost}:${BM_DB_PORT:5432}/blimfy
    user: ${BM_DB_USERNAME:blimfy_admin}
    password: ${BM_DB_PASSWORD:blimfy_password}
    driver-class-name: org.postgresql.Driver
```

- **BM_DB_HOST:** хост подключения к БД;
- **BM_DB_PORT:** порт подключения к БД;
- **BM_DB_USERNAME:** имя пользователя подключения к БД;
- **BM_DB_PASSWORD:** пользователь подключения к БД.