## producer-service: 
Открыт для HTTP-запросов
Для тестирования кафки: http://localhost:8080/message?message=hello
Сообщение отправляет в топик
## consumer-service:
Имеет листенер, который прослушивает наш топик и выводит сообщение в логи
