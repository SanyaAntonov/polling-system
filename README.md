**REST / Spring Boot / Security / JPA / H2 / Lombok / Swagger UI**
##
**Тестовое задание**
##

This Java project offers a `RESTful API` with basic authentication for admin and regular users.


**Задача: спроектировать и разработать API для системы опросов пользователей.**
>Функционал для администратора системы:

- авторизация в системе (регистрация не нужна)
- добавление/изменение/удаление опросов. Атрибуты опроса: название, дата старта, дата окончания, описание. После создания поле "дата старта" у опроса менять нельзя
- добавление/изменение/удаление вопросов в опросе. Атрибуты вопросов: текст вопроса, тип вопроса (ответ текстом, ответ с выбором одного варианта, ответ с выбором нескольких вариантов)

>Функционал для пользователей системы:

- получение списка активных опросов
- прохождение опроса: опросы можно проходить анонимно, в качестве идентификатора пользователя в API передаётся числовой ID, по которому сохраняются ответы пользователя на вопросы; один пользователь может участвовать в любом количестве опросов
- получение пройденных пользователем опросов с детализацией по ответам (что выбрано) по ID уникальному пользователя

**Technology stack:**
- Spring Boot
- Spring Security
- REST
- Spring Data JPA
- H2 DB
- Maven
- Lombok
- Swagger UI

##
**How to use this program**

**1. Clone a repository:**

```sh
 git clone https://github.com/SanyaAntonov/
```

**2. Open the project using the IDE**

**3. Run your program.**

**4. Connect to H2 database in the IDE to see all changes with parameters :**
```sh
  URL: jdbc:h2:tcp://localhost:9092/mem:polling
  User: sa
  Password:
```

**5. Use Swagger UI to test this API :**
```sh
  http://localhost:8080/swagger-ui.html#/
```
##