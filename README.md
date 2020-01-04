# calvin-framework
自己动手写web框架
初版：
写了一个小的MVC框架:
支持@ApiPath，@Autowire，@Endpoint，@Service四个注解。
通过tomcat回调ContextLifeListener来初始化IOC容器，将@Service注入到@Endpoint中。
建立了DistributeServlet类来处理请求。
