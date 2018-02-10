package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class PlainJsonLoggerConfiguration extends WebMvcConfigurerAdapter {

	//@Bean
	public CommonsRequestLoggingFilter CommonsRequestLoggingFilter(){
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeClientInfo(true);
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		return filter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("addInterceptors was called");

		HandlerInterceptorAdapter interceptor = new HandlerInterceptorAdapter() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
				if (!"application/json".equals(request.getContentType())) {
					return true;
				}
				ContentCachingRequestWrapper requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
				requestCacheWrapperObject.getParameterMap();
				String characterEncoding = request.getCharacterEncoding();

				byte[] body = requestCacheWrapperObject.getContentAsByteArray();
				String json = new String(body, characterEncoding);

				System.out.println(json);


				return true;
			}

			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
				if (!"application/json".equals(response.getContentType())) {
					return;
				}

			}
		};
		registry.addInterceptor(interceptor);
	}
}
