package com.bangduoduo.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.bangduoduo.common.Constants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	private Configuration configuration;

	private static FreemarkerUtil freemarkerUtil = new FreemarkerUtil();

	private FreemarkerUtil() {
		configuration = new Configuration();
		configuration.setClassForTemplateLoading(getClass(),
				Constants.FREEMARKER_TEMPLATE_PATH);
	}

	private static Template getTemplateByFileName(String templateName)
			throws IOException {
		return freemarkerUtil.configuration.getTemplate(templateName);
	}

	private static String transfer(Template template, Object model,
			Map<String, Object> additionProperties) throws TemplateException,
			IOException {
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("model", model);
		if (additionProperties != null)
			root.put("property", additionProperties);
		template.process(root, writer);
		writer.flush();
		return stringWriter.toString();
	}

	public static String transfer(String templateName, Object model,
			Map<String, Object> additionProperties) throws TemplateException,
			IOException {
		Template template = getTemplateByFileName(templateName);
		return transfer(template, model, additionProperties);
	}
}