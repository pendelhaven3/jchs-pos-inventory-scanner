package com.jchs.pos.inventoryscanner.component;

import com.jchs.pos.inventoryscanner.model.Entry;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ButtonCellFactory implements Callback<TableColumn<Entry, Entry>, TableCell<Entry, Entry>> {

	@Override
	public TableCell<Entry, Entry> call(TableColumn<Entry, Entry> param) {
		return new TableCell<Entry, Entry>() {
			
			private final Button deleteButton = new Button("Delete");
			
			@Override
			protected void updateItem(Entry entry, boolean empty) {
				super.updateItem(entry, empty);
				
				if (entry == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> getTableView().getItems().remove(entry));
			}
		};
	}

}
