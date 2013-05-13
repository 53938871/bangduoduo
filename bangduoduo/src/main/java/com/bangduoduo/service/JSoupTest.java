package com.bangduoduo.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("http://jsoup.org/cookbook/input/load-document-from-url").get();
			String title = doc.title();
			
			Elements e = doc.select("div.breadcrumb");
			Element e1 = e.first();
			System.out.println(e1.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
