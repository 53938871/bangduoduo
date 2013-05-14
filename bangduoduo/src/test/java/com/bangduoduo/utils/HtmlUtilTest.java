package com.bangduoduo.utils;

import junit.framework.Assert;

import org.junit.Test;

public class HtmlUtilTest {
	private String content = "<script>alert(\"ok\")</script>";
	
	@Test
	public void should_return_encode_html(){
		String expected = "&lt;script&gt;alert(&quot;ok&quot;)&lt;/script&gt;";
		Assert.assertEquals(expected,WebUtil.encodeHtml(content));
	}
	
	@Test
	public void should_return_decode_html(){
		String encodeStr = "&lt;script&gt;alert(&quot;ok&quot;)&lt;/script&gt;";
		Assert.assertEquals(content,WebUtil.decodeHtml(encodeStr));
	}
	
}
