package br.com.metronic.viewresolver.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;


public class SampleRssViewer extends AbstractRssFeedView {

	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel channel, HttpServletRequest request) {
		channel.setTitle("Metronic Samples");
		channel.setLink("http://lncgarcia/metronic");
		channel.setDescription("Web features in a sample application");
		super.buildFeedMetadata(model, channel, request);
	}
	
	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Product product = (Product) model.get("product");
		List<Item> items = new ArrayList<Item>(1);
		
		Item item = new Item();
		
		Content content = new Content();
		content.setValue("Any description");
		item.setContent(content);
		
		item.setTitle("Feed title");
		item.setLink("http://localhost:8080/metronic/"+"oobjectId");
		item.setPubDate(new Date());
		
		items.add(item);
		
		return items;
	}


}
