package com.jeroenreijn.examples.view;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractTemplateView;

import com.fizzed.rocker.ContentType;
import com.fizzed.rocker.Rocker;
import com.fizzed.rocker.runtime.OutputStreamOutput;

public class RockerView extends AbstractTemplateView {
	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		try (OutputStream os = response.getOutputStream()) {
			Rocker.template("index.rocker.html")
					.bind("presentations", model.get("presentations"))
					.bind("i18n", model.get("i18n"))
					.render((contentType, charsetName) -> new OutputStreamOutput(contentType, os, charsetName));
		}
	}
}
