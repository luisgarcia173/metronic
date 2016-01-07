package br.com.metronic.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.metronic.viewresolver.CustomXMLViewResolver;
import br.com.metronic.viewresolver.JsonViewResolver;
import br.com.metronic.viewresolver.SampleRssViewResolver;

@EnableWebMvc
//@ComponentScan(basePackageClasses = { HomeController.class, UserDAO.class, FileSaver.class, User.class })
@ComponentScan(basePackages = { "br.com.metronic.controllers", "br.com.metronic.daos", "br.com.metronic.models", "br.com.metronic.utils"})
@EnableCaching
@PropertySource("classpath:/mail.properties")
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;
	
	/**
	 * Responsible for register the page suffix, path 
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	/**
	 * Responsible for register ViewResolvers [HTML/JSON/XML/RSS]
	 * @param manager
	 * @return
	 */
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		//Step 1: Resolver list
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		
		//Step 2: Add resolvers
		resolvers.add(internalResourceViewResolver()); //view resolver for html
		resolvers.add(new JsonViewResolver()); // view resolver for json
		resolvers.add(getMarshallingXmlViewResolver()); // view resolver for xml
		resolvers.add(new SampleRssViewResolver()); // view resolver for rss: object Sample
		
		//Step 3: Register the resolvers
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
		return resolver;
	}
	
	/**
	 * Resposible for register the classes to be parsed as XML
	 * @return
	 */
	@Bean
	public CustomXMLViewResolver getMarshallingXmlViewResolver() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// One Object
		//marshaller.setClassesToBeBound(Product.class);
		
		// Two or more objects
//		XStreamMarshaller marshaller = new XStreamMarshaller();
//		HashMap<String, Class<?>> keys = new HashMap<String,Class<?>>();
//		keys.put("product", Product.class);
//		keys.put("price", Price.class);
//		marshaller.setAliases(keys);
		return new CustomXMLViewResolver(marshaller);
	}

	/**
	 * Responsible for register the messages (properties) and i18n
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("/WEB-INF/messages");
		bundle.setDefaultEncoding("UTF-8");
		bundle.setCacheSeconds(1); // Cache for Messages
		return bundle;
	}

	/**
	 * Responsible for register the date formatting
	 * @return
	 */
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(true);
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
		registrar.registerFormatters(conversionService);
		return conversionService;
	}

	/**
	 * Resposible for allow the file uploading
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	/**
	 * Resposible for register the RestTemplate as BEAN, in order to consume REST services easely
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * Responsible for enable Spring Cache
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(){
		//return new ConcurrentMapCacheManager(); //Simple way to use cache, no config, no time management, no size
		
		// Using guava for caching management
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
			.maximumSize(100)
			.expireAfterAccess(5, TimeUnit.MINUTES);
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		cacheManager.setCacheBuilder(builder);
		return cacheManager;
	}
	
	/**
	 * Responsible for enable the Internationalization
	 * @return
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
	
	/**
	 * Resposible for redirect unknown url to servlet container (Tomcat), used for static files [css/js/images] 
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * Responsible for enable the email sender interface
	 * @return
	 */
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(env.getProperty("mail.host"));
		javaMailSenderImpl.setPassword(env.getProperty("mail.password"));
		javaMailSenderImpl.setPort(Integer.valueOf(env.getProperty("mail.port")));
		javaMailSenderImpl.setUsername(env.getProperty("mail.username"));

		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		
		javaMailSenderImpl.setJavaMailProperties(mailProperties);
		return javaMailSenderImpl;
	}

}
