package fatec.com.digital_library.entity;

import java.util.List;

public class ShoppingCart {

	private List<Item> itemList;
	private Double allItemsValue;

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Double getAllItemsValue() {
		return allItemsValue;
	}

	public void setAllItemsValue(Double allItemsValue) {
		this.allItemsValue = allItemsValue;
	}

}
