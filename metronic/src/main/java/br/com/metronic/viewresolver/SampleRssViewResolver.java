package br.com.metronic.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import br.com.metronic.viewresolver.view.SampleRssViewer;

public class SampleRssViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		SampleRssViewer view = new SampleRssViewer();
		return view;
	}

}
