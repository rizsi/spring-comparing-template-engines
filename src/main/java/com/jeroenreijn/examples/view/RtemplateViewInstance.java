package com.jeroenreijn.examples.view;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.web.servlet.support.RequestContext;

import com.jeroenreijn.examples.model.Presentation;
import com.jeroenreijn.examples.model.i18nLayout;

/**
 * With Rtemplate an object field is used to store the context of the query
 * and the template output object (Writer in this case). Thus a new object is created for
 * each query. This is the object that serves a single query.
 */
public class RtemplateViewInstance {
	private PrintWriter writer;
	private Iterable<Presentation> presentations;
	private i18nLayout i18n;
	private RequestContext context;
	/**
	 * Create object that servers a single query and stores all data sources.
	 * @param context
	 * @param writer
	 * @param presentations
	 * @param i18n
	 */
	public RtemplateViewInstance(RequestContext context, PrintWriter writer, Iterable<Presentation> presentations, i18nLayout i18n) {
		this.context=context;
		this.writer=writer;
		this.presentations=presentations;
		this.i18n=i18n;
	}
	/**
	 * Execute rendering of the page into the stored writer object.
	 */
	public void render() {
		writer.write("<!DOCTYPE html>\n<html>\n");
		head();
		writer.write("    <body>\n        <div class=\"container\">\n            <div class=\"pb-2 mt-4 mb-3 border-bottom\">\n                <h1>");
		writeHtml(i18n.message("example.title"));
		writer.write(" - Rtemplate</h1>\n            </div>\n");
		for(Presentation p: presentations)
		{
			presentation(p);
		}
		writer.write("        </div>\n");
		scripts();
		writer.write("    </body>\n</html>\t\n");
	}
	/**
	 * Write a string through HTML escaping to the writer.
	 * For the sake of simplicity no library is used. In real world usage this would
	 * be backed by an escaping library.
	 * @param message
	 */
	private void writeHtml(String message) {
		for(int i=0;i<message.length();++i)
		{
			char ch=message.charAt(i);
			switch (ch) {
			case '<':
				writer.write("&lt;");
				break;
			case '>':
				writer.write("&gt;");
				break;
			case '&':
				writer.write("&amp;");
				break;
			case '"':
				writer.write("&quot;");
				break;
			default:
				writer.write(ch);
				break;
			}
		}
	}
	/**
	 * Render a single presentation.
	 * @param p
	 */
	private void presentation(Presentation p) {
		writer.write("<div class=\"card mb-3 shadow-sm rounded\">\n    <div class=\"card-header\">\n        <h5 class=\"card-title\">");
		writer.write(p.getTitle());
		writer.write(" - ");
		writer.write(p.getSpeakerName());
		writer.write("</h5>\n    </div>\n    <div class=\"card-body\">\n        ");
		writer.write(p.getSummary());
		writer.write("\n    </div>\n</div>\n");
	}
	/** Render the head part of the HTML page. */
	private void head() {
		writer.write("<head>\n    <meta charset=\"UTF-8\"/>\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\n    <title>");
		writer.write(i18n.message("example.title"));
		writer.write(" - Rtemplate</title>\n    <link rel=\"stylesheet\" href=\"");
		writer.write(context.getContextPath());
		writer.write("/webjars/bootstrap/4.3.1/css/bootstrap.min.css\"/>\n</head>\n");
	}
	/** Render the scripts part of the HTML page. */
	private void scripts() {
		writer.write("<script src=\"");
		writer.write(context.getContextPath());
		writer.write("/webjars/jquery/3.1.1/jquery.min.js\"></script>\n<script src=\"");
		writer.write(context.getContextPath());
		writer.write("/webjars/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n");
	}
}
