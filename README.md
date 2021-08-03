## GETTING STARTED WITH THE SIMPLE-REST APP

### Project dependencies
- OpenJDK 8
- Maven

### Running the application locally

There are several ways to run this application. One way is to execute the `main` method in the `Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### Running tests
```
mvn test
```

### API

#### /api/quotes
* `GET` : Get all quotes
* `POST` : Create a new quote

#### /api/quotes/:id
* `GET` : Get quote
* `PUT` : Change quote
* `PATCH`: Update content of the quote
* `DELETE` : Delete quote

#### examples of usage:

```
curl -X GET --location "http://localhost:8080/api/quotes"

GET http://localhost:8080/api/quotes

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: XXX
Keep-Alive: timeout=60
Connection: keep-alive

[
  {
    "id": 1,
    "author": "John Doe",
    "content": "Lorem ipsum"
  },
  {
    "id": 2,
    "author": "John Doe",
    "content": "dolor sit amet, consectetur adipiscing elit"
  }
]

Response code: 200; Time: 210ms; Content length: 2 bytes
```
--
```
curl -X POST --location "http://localhost:8080/api/quotes/" \
    -H "Content-Type: application/json" \
    -d "{
          \"firstName\": \"Jan\",
          \"lastName\": \"Kowalski\",
          \"quote\": \"If you are going through hell, keep going\"
        }"

POST http://localhost:8080/api/quotes/

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: XXXX
Keep-Alive: timeout=60
Connection: keep-alive

{
  "id": 3,
  "author": "Jan Kowalski",
  "content": "If you are going through hell, keep going"
}

Response code: 200; Time: 235ms; Content length: 86 bytes
```
--
```
GET http://localhost:8080/api/quotes/2

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: XXXX
Keep-Alive: timeout=60
Connection: keep-alive

{
  "id": 2,
  "author": "Mark Twain",
  "content": "Twenty years from now you will be more disappointed by the things that you didn’t do than by the ones you did do"
}

Response code: 200; Time: 103ms; Content length: 156 bytes
```

```
curl -X PATCH --location "http://localhost:8080/api/quotes/1" \
    -H "Content-Type: text/plain; charset=utf-8" \
    -d "Knowledge is power."

PATCH http://localhost:8080/api/quotes/1

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: XXXX
Keep-Alive: timeout=60
Connection: keep-alive

{
  "id": 1,
  "author": "John Doe",
  "content": "Knowledge is power."
}

Response code: 200; Time: 180ms; Content length: 60 bytes

```
--
```
curl -X PUT --location "http://localhost:8080/api/quotes/2" \
    -H "Content-Type: application/json" \
    -d "{
          \"firstName\": \"Piotr\",
          \"lastName\": \"Nowak\",
          \"quote\": \"Twenty years from now you will be more disappointed by the things that you didn’t do than by the ones you did do\"
        }"

PUT http://localhost:8080/api/quotes/2

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: XXXX
Keep-Alive: timeout=60
Connection: keep-alive

{
  "id": 2,
  "author": "Piotr Nowak",
  "content": "Twenty years from now you will be more disappointed by the things that you didn’t do than by the ones you did do"
}

Response code: 200; Time: 118ms; Content length: 156 bytes        
```
--
```
DELETE http://localhost:8080/api/quotes/1

HTTP/1.1 200 
Content-Length: 0
Date: Tue, 03 Aug 2021 01:39:15 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>

Response code: 200; Time: 229ms; Content length: 0 bytes
```
