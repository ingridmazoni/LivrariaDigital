package fatec.com.digital_library.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart {

	private List<Item> itemList = Collections.synchronizedList(new ArrayList<Item>());
	private Double allItemsValue = 0.0;

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(Item item) {
		this.itemList.add(item);
	}

	public Double getAllItemsValue() {
		if (!itemList.isEmpty()){
			allItemsValue = 0.0;
			for (Item item: itemList) {
				allItemsValue += item.getTotalValue();
			}
		}
		return allItemsValue;
	}

	public void setAllItemsValue(Double allItemsValue) {
		this.allItemsValue = allItemsValue;
	}

}
