# SpringBoot Project에서 Servlet 작성하기

Spring Boot Project에서도 Servlet API를 사용하여 직접 Servlet을 작성할 수 있다.
@WebServlet, @WebFilter, @WebListener와 같은 Servlet 애노테이션을 스캔하려면 메인 클래스에 @ServletComponentScan을 추가한다.

```java
@SpringBootApplication
@ServletComponentScan
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

## Servlet 작성

Servlet은 @WebServlet 애노테이션을 사용하여 등록할 수 있으며, HttpServlet을 상속받아 doGet(), doPost() 등의 메서드를 오버라이드하여 요청을 처리한다.

```java@WebServlet("*.do")
public class DemoServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        // request 처리
    }
}
```

이때 오버라이드한 메서드에서 다음과 같이 부모 클래스의 메서드를 호출하지 않도록 주의한다.

super.doGet(req, resp);   // 호출하지 않음

HttpServlet의 기본 doGet() 구현은 GET 요청을 처리하지 않는 경우 405 Method Not Allowed 응답을 생성하므로, 직접 doGet()을 구현했다면 부모 메서드를 호출할 필요가 없다.

## Filter 작성

웹 필터는 @WebFilter 애노테이션을 사용하여 등록할 수 있으며, Filter 인터페이스를 구현한다.

필터에서는 Servlet이나 Spring MVC의 DispatcherServlet으로 요청이 전달되기 전과 응답이 반환된 후에 공통 로직을 수행할 수 있다.

```java
@WebFilter("*.do")
@Slf4j
public class DemoFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        log.info("{} starts at {}",
                httpRequest.getRequestURI(),
                LocalDateTime.now());

        chain.doFilter(request, response);

        log.info("{} returns at {}",
                httpRequest.getRequestURI(),
                LocalDateTime.now());
    }
}
```

웹 필터는 @WebFilter 애노테이션을 사용하여 작성할 수 있으며 Filter 인터페이스를 구현한다.

```java
@WebFilter("*.do")
@Slf4j
public class DemoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("{} starts at {}", httpRequest.getRequestURI(), LocalDateTime.now());
        chain.doFilter(request, response);
        log.info("{} returns at {}", httpRequest.getRequestURI(), LocalDateTime.now());
    }
}
```

chain.doFilter()을 기준으로 요청 처리 전과 후를 구분할 수 있다.

## Listener 작성

Listener @WebListener 애노테이션을 사용하여 등록할 수 있다.
Listener는 웹 애플리케이션의 시작과 종료, 세션 생성과 종료 등의 Servlet 컨테이너 이벤트를 감지할 때 사용한다.

예를 들어 애플리케이션의 시작과 종료를 감지하려면 ServletContextListener를 구현한다.

```java
@WebListener
@Slf4j
public class DemoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Web application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Web application stopped");
    }
}
```

Spring Boot 애플리케이션이 시작되면 contextInitialized()가 호출되고, 애플리케이션이 종료되면 contextDestroyed()가 호출된다.

세션의 생성과 종료를 감지하려면 HttpSessionListener를 사용할 수 있다.

```java
@WebListener
@Slf4j
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("Session created: {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("Session destroyed: {}", se.getSession().getId());
    }
}
```

Servlet 관련 주요 애노테이션을 정리하면 다음과 같다.

| 애노테이션 | 역할 |
|---|---|
| `@WebServlet` | HTTP 요청을 직접 처리하는 Servlet 등록 |
| `@WebFilter` | 요청과 응답 전후에 공통 로직 수행 |
| `@WebListener` | 애플리케이션, 세션 등의 이벤트 감지 |

Spring Boot에서는 일반적으로 @Controller, @RestController를 사용하여 요청을 처리하지만, 이러한 Servlet API를 직접 사용해보면 Spring MVC가 Servlet 기반 위에서 동작한다는 구조를 이해하는 데 도움이 된다.