package fatec.com.digital_library.entity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<Item> itemList = new ArrayList<Item>();;
	private Double allItemsValue = 0.0;

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(Item item) {
		this.itemList.add(item);
	}

	public Double getAllItemsValue() {
		if (!itemList.isEmpty()){
			for (Item item: itemList) {
				allItemsValue = 0.0;
				allItemsValue += item.getTotalValue();
			}
		}
		return allItemsValue;
	}

	public void setAllItemsValue(Double allItemsValue) {
		this.allItemsValue = allItemsValue;
	}

}
