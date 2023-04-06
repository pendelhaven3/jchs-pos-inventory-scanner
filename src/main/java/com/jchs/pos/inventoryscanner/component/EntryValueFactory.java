package com.jchs.pos.inventoryscanner.component;

import com.jchs.pos.inventoryscanner.model.Entry;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class EntryValueFactory implements Callback<CellDataFeatures<Entry, Entry>, ObservableValue<Entry>> {

	public ObservableValue<Entry> call(CellDataFeatures<Entry, Entry> param) {
		return new ReadOnlyObjectWrapper<>(param.getValue());
	}

}
