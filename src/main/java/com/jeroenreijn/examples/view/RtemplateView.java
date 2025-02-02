package com.jeroenreijn.examples.view;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.jeroenreijn.examples.model.Presentation;

public class RtemplateView extends AbstractTemplateView {
	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object rcontext=model.get("springMacroRequestContext");
		@SuppressWarnings("unchecked")
		RequestContext context=(RequestContext)model.get("springMacroRequestContext");
		@SuppressWarnings("unchecked")
		Iterable<Presentation> presentations=(Iterable<Presentation>)model.get("presentations");
		com.jeroenreijn.examples.model.i18nLayout i18n=(com.jeroenreijn.examples.model.i18nLayout)model.get("i18n");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		try (OutputStream os = response.getOutputStream()) {
			// PrintStream ps=new PrintStream(os, false, StandardCharsets.UTF_8);
			RtemplateViewInstance vi=new RtemplateViewInstance(context, os, presentations, i18n);
			vi.render();
		}
	}
}
