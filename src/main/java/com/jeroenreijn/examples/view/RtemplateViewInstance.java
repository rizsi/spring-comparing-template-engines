package com.jeroenreijn.examples.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.web.servlet.support.RequestContext;

import com.jeroenreijn.examples.model.Presentation;
import com.jeroenreijn.examples.model.i18nLayout;

/**
 * With Rtemplate an object field is used to store the context of the query
 * and the template output object (Writer in this case). Thus a new object is created for
 * each query. This is the object that serves a single query.
 */
public class RtemplateViewInstance {
	private OutputStream os;
	private Iterable<Presentation> presentations;
	private i18nLayout i18n;
	private RequestContext context;
	private CharsetEncoder encoder=StandardCharsets.UTF_8.newEncoder();
	CharBuffer cb=CharBuffer.wrap(new char[4096]);
	byte[] arr=new byte[4096*5];
	ByteBuffer bb=ByteBuffer.wrap(arr);
	static private StringCache sc=new StringCache();
	/**
	 * Create object that servers a single query and stores all data sources.
	 * @param context
	 * @param writer
	 * @param presentations
	 * @param i18n
	 */
	public RtemplateViewInstance(RequestContext context, OutputStream os, Iterable<Presentation> presentations, i18nLayout i18n) {
		this.context=context;
		this.os=os;
		this.presentations=presentations;
		this.i18n=i18n;
	}
	private void write(String s) throws IOException
	{
		sc.write(os, s);
	}
	private void writeValue(String s)  throws IOException 
	{
		os.write(s.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * Execute rendering of the page into the stored writer object.
	 */
	public void render()  throws IOException {
		write("<!DOCTYPE html>\n<html>\n");
		head();
		write("    <body>\n        <div class=\"container\">\n            <div class=\"pb-2 mt-4 mb-3 border-bottom\">\n                <h1>");
		writeHtml(i18n.message("example.title"));
		write(" - Rtemplate</h1>\n            </div>\n");
		for(Presentation p: presentations)
		{
			presentation(p);
		}
		write("        </div>\n");
		scripts();
		write("    </body>\n</html>\t\n");
	}
	/**
	 * Write a string through HTML escaping to the writer.
	 * For the sake of simplicity no library is used. In real world usage this would
	 * be backed by an escaping library.
	 * @param message
	 */
	private void writeHtml(String message)  throws IOException {
		cb.clear();
		if(message.length()>2048)
		{
			throw new RuntimeException("Too long");
		}
		for(int i=0;i<message.length();++i)
		{
			char ch=message.charAt(i);
			switch (ch) {
			case '<':
				cb.append("&lt;");
				break;
			case '>':
				cb.append("&gt;");
				break;
			case '&':
				cb.append("&amp;");
				break;
			case '"':
				cb.append("&quot;");
				break;
			default:
				cb.append(ch);
				break;
			}
		}
		bb.clear();
		cb.flip();
		encoder.encode(cb, bb, true);
		os.write(arr, 0, bb.position());
	}
	/**
	 * Render a single presentation.
	 * @param p
	 */
	private void presentation(Presentation p)  throws IOException {
		write("<div class=\"card mb-3 shadow-sm rounded\">\n    <div class=\"card-header\">\n        <h5 class=\"card-title\">");
		writeHtml(p.getTitle());
		write(" - ");
		writeHtml(p.getSpeakerName());
		write("</h5>\n    </div>\n    <div class=\"card-body\">\n        ");
		writeValue(p.getSummary());
		write("\n    </div>\n</div>\n");
	}
	/** Render the head part of the HTML page. */
	private void head()  throws IOException {
		write("<head>\n    <meta charset=\"UTF-8\"/>\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\n    <title>");
		writeHtml(i18n.message("example.title"));
		write(" - Rtemplate</title>\n    <link rel=\"stylesheet\" href=\"");
		writeValue(context.getContextPath());
		write("/webjars/bootstrap/4.3.1/css/bootstrap.min.css\"/>\n</head>\n");
	}
	/** Render the scripts part of the HTML page. */
	private void scripts()  throws IOException {
		write("<script src=\"");
		writeValue(context.getContextPath());
		write("/webjars/jquery/3.1.1/jquery.min.js\"></script>\n<script src=\"");
		writeValue(context.getContextPath());
		write("/webjars/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n");
	}
}
