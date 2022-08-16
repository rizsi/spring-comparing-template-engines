package com.jeroenreijn.examples.view;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/**
 * Connect /rtemplate URL to the RTempalte implementation of the page.
 */
public class RtemplateViewResolver extends AbstractTemplateViewResolver {
	public RtemplateViewResolver() {
		this.setViewClass(this.requiredViewClass());
	}
	@Override
	protected Class<?> requiredViewClass() {
		return RtemplateView.class;
	}
}
