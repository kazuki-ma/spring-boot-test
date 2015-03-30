package la.serendipity;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.bull.javamelody.MonitoringFilter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@ImportResource("classpath:net/bull/javamelody/monitoring-spring.xml")
public class MvcConfiguration extends WebMvcConfigurationSupport{
    @Bean
    public FilterRegistrationBean shallowEtagHeaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Filter(){
			@Override
			public void init(FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request,
					ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				System.out.println("TEST");
				chain.doFilter(request, response);
			}

			@Override
			public void destroy() {
			}
        });
        return registration;
    }
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        Filter javaMelodyFilter = new MonitoringFilter();
        FilterRegistrationBean javaMelodyFilterBean = new FilterRegistrationBean(javaMelodyFilter);
        javaMelodyFilterBean.addServletNames("monitoring");
        javaMelodyFilterBean.addUrlPatterns("/*");
        return javaMelodyFilterBean;
    }
}
