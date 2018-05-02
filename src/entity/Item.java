package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//用来处理ticketmaster里得到的数据
/*
 * Builder Patter 方法
 * ItemBuilder builder= newItemBuilder();
 * builder.set...(set所有需要的变量）
 * Item item = builder.buil(); 一但call build就不能再set
 * 假如不用build pattern
 * Item item = new Item(id, name, rating, ...);
 * Item item = new Item(id, name);
 * 需要有各种constructor, 写起来十分麻烦。
 * 但假如 Item里面的field都有setter ，builder pattern就没有意义，但一般不会存在能直接set private field 的情况
 * */
public class Item {
	private String itemId;
	private String name;
	private double rating;
	private String address;
	private Set<String> categories;
	private String imageUrl;
	private String url;
	private double distance;
	/**
	 * This is a builder pattern in Java.
	 */
	//private constructor的原因：如果想创建item object就是通过itembuilder.build, 如果constructor是public，就可以直接调用constructor
	//失去了了item builder的意义，有很多private的field，需要有各种排列组合的constructor
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.name = builder.name;
		this.rating = builder.rating;
		this.address = builder.address;
		this.categories = builder.categories;
		this.imageUrl = builder.imageUrl;
		this.url = builder.url;
		this.distance = builder.distance;
	}

	public String getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public double getRating() {
		return rating;
	}
	public String getAddress() {
		return address;
	}
	public Set<String> getCategories() {
		return categories;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public double getDistance() {
		return distance;
	}
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("item_id", itemId);
			obj.put("name", name);
			obj.put("rating", rating);
			obj.put("address", address);
			obj.put("categories", new JSONArray(categories));
			obj.put("image_url", imageUrl);
			obj.put("url", url);
			obj.put("distance", distance);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	//不知道会用哪个constructor建立Item
	//builder pattern
	//static 的原因：item通过itembuilder来的，但是用itembuilder需要item instance
	//inner class的原因：因为item的constructor是private，不是inner class无法调用item的constructor
	
	public static class ItemBuilder {
		private String itemId;
		private String name;
		private double rating;
		private String address;
		private Set<String> categories;
		private String imageUrl;
		private String url;
		private double distance;

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setRating(double rating) {
			this.rating = rating;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setCategories(Set<String> categories) {
			this.categories = categories;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}
		public Item build() {
			return new Item(this);
		}

	}



}
