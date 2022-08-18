package com.jeroenreijn.examples.view;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/**
 * Connect /rtemplate URL to the RTempalte implementation of the page.
 */
public class EmptyViewResolver extends AbstractTemplateViewResolver {
	public EmptyViewResolver() {
		this.setViewClass(this.requiredViewClass());
	}
	@Override
	protected Class<?> requiredViewClass() {
		return EmptyView.class;
	}
}
