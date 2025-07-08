## producer-service: 
Открыт для HTTP-запросов

Для тестирования кафки: http://localhost:8080/message?message=hello

Сообщение отправляет в топик
## consumer-service:
Имеет листенер, который прослушивает наш топик и выводит сообщение в логи


curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/ -d "@connector.json"

проверка статуса коннектора: curl -i http://localhost:8083/connectors/producer-service-connector/status 


проверка топиков: docker-compose exec kafka kafka-topics --list --bootstrap-server kafka:9092


curl -X POST http://localhost:8080/users -H "Content-Type: application/json"  -H "Accept: application/json" -d "{\"name\":\"Alice\",\"email\":\"alice@example.com\"}"


curl "http://localhost:8080/users/1"


curl -X PUT http://localhost:8080/users/1 -H "Content-Type: application/json" -H "Accept: application/json"  -d "{\"name\": \"Updated Name\",\"email\": \"updated@example.com\"}"


curl -X DELETE http://localhost:8080/users/1 -H "Accept: application/json"


curl -X POST http://localhost:8080/orders -H "Content-Type: application/json"  -H "Accept: application/json" -d "{\"userId\":\"1\",\"amount\":\"100\",\"status\":\"NEW\"}"


curl "http://localhost:8080/orders/1"