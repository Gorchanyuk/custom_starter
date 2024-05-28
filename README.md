# Logger http Spring Boot Starter

Проект состоит из двух сервисов:
- **logger-http-spring-boot-starter** - стартер для логировния http запросов
- **example-work-starter** - простой проект для просмотра возможностей стартера


## **logger-http-spring-boot-starter** 
Представляет собой Spring Boot Starter, который предоставит возможность логировать 
HTTP запросы в вашем приложении 
на базе Spring Boot.

Стартер предоставляет возможность логировать все входящие и исходящие HTTP запросы и 
ответы вашего приложения. Логирование включает в себя метод запроса, URL, 
заголовки запроса и ответа, код ответа, время обработки запроса,а также сообщения о 
возникших во время обработки запроса исключениях.

### Установка и использование стартера
#### Установка в локальный репозиторий
Для начала работы со стартером, нужно добавить его в локальный репозиторий.
Для этого перейдите в дирректорию *.\custom_starter\logger-http-spring-boot-starter*
и выполните команду

`mvn clean install`

После этого стартер будет установлен в локальный репозиторий и вы сможете использовать
его в своих проектах.
#### Добавление в проект
Для добавления стартера в ваш проект добавьте в ваш .pom файл следующую зависимость

<pre><code>&lt;dependency&gt;
    &lt;groupId&gt;ru.gorchanyuk&lt;/groupId&gt;
    &lt;artifactId&gt;logger-http-spring-boot-starter&lt;/artifactId&gt;
    &lt;version&gt;0.0.1-SNAPSHOT&lt;/version&gt;
&lt;/dependency&gt;</code></pre>

#### Работа со стартером
Стартер обеспечивает гибкие возможности для управления логированием входящих и исходящих
http апросов. Для управления поведением стартера, в нем реализованны следующие 
конфигурационные свойства с параметрами по умолчанию:
- `logger-http.interceptor.enabled=true`
- `logger-http.interceptor.pre-handle-enabled=false`
- `logger-http.interceptor.post-handle-enabled=false`
- `logger-http.interceptor.after-completion-enabled=true`
- `logger-http.interceptor.pre-handle-level=info`
- `logger-http.interceptor.post-handle-level=info`
- `logger-http.interceptor.after-completion-level=error`
- `logger-http.interceptor.logging-with-headers=false`

В стартере реализована подробная документация с возможными значениями и их назначением
для каждого свойства.
##### Свойство `logger-http.interceptor.enabled`
Позволяет отключить стартер, не удаляя его зависимость из проекта.
- `true` включенно
- `false` отключить

По умолчанию: `true`
##### Свойство `logger-http.interceptor.pre-handle-enabled`
Включает/отключает логирование входящих http запросов
- `true` включенно
- `false` отключить

По умолчанию: `false`
##### Свойство `logger-http.interceptor.post-handle-enabled`
Включает/отключает логирование исходящих http запросов
- `true` включенно
- `false` отключить

По умолчанию: `false`
##### Свойство `logger-http.interceptor.after-completion-enabled`
Включает/отключает логирование об ошибке во время выполнения http запроса
- `true` включенно
- `false` отключить

По умолчанию: `true`
##### Свойства:  
`logger-http.interceptor.pre-handle-level`, 
`logger-http.interceptor.post-handle-level`, 
`logger-http.interceptor.after-completion-level`

Отвечают за уровень логирования соответствующего этапа запроса. 
Могут иметь следующие варианты значений:
- `trace` - Уровень логирования TRACE
- `debug` - Уровень логирования DEBUG
- `info` - Уровень логирования INFO
- `warn` - Уровень логирования WARN
- `error` - Уровень логирования ERROR

По умолчанию:
- `info` - для логирования входящих и исходящих запросов
- `error` - для логирования запросов завершившихся ошибкой

##### Свойство `logger-http.interceptor.logging-with-headers`
Включает/отключает вывод в логах информации о заголовках запросов
- `true` включенно
- `false` отключить

По умолчанию: `false`

## **example-work-starter**
Данный сервис не несет никакой полезной нагрузки и служит лишь для демонстрации 
работы стартера. В сервисе добавленна библиотека **OpenAPI**, через которую удобно
отправлять запросы, результаты которого будут логироваться подключенным стартером. 
*API* доступно по адресу: 
- http://localhost:8079/swagger-ui/index.html

Для удобства, в файле application.properties уже добавленны все свойства стартера. 
Просто расскоментируйте и/или измените значение интересующего вас свойства, после чего
перезапустите проект и поотправляйте запросы через *API*.
