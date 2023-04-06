package com.jchs.pos.inventoryscanner.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.pos.inventoryscanner.component.ShowDialog;
import com.jchs.pos.inventoryscanner.excel.InventoryMonitoringSheetExcelGenerator;
import com.jchs.pos.inventoryscanner.model.Entry;
import com.jchs.pos.inventoryscanner.model.Product;
import com.jchs.pos.inventoryscanner.service.ProductService;
import com.jchs.pos.inventoryscanner.util.ExcelUtil;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ScanController extends AbstractController {

    private static final int MAX_ENTRIES = 21;
    
	@Autowired
	private ProductService productService;
	
	@FXML private TextField barcodeField;
	@FXML private TextField descriptionField;
	@FXML private RadioButton unitCase;
	@FXML private RadioButton unitTie;
	@FXML private RadioButton unitPck;
	@FXML private RadioButton unitHdoz;
	@FXML private RadioButton unitPcs;
	@FXML private TextField quantityField;
	@FXML private TableView<Entry> entriesTable;
	
	private boolean autosearch = true;
	
	@FXML
	public void initialize() {
		barcodeField.setOnKeyReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				String value = barcodeField.getText();
				if (value.length() == 12) {
					if (!autosearch) return;
					
					Product product = productService.findByBarcode(value);
					if (product != null) {
						descriptionField.setText(product.getDescription());
						unitCase.setSelected(true);
						quantityField.clear();
						unitCase.requestFocus();
					} else {
						ShowDialog.error("Barcode not found");
					}
					autosearch = false;
				} else {
					descriptionField.clear();
					unitCase.setSelected(false);
					unitTie.setSelected(false);
					unitPck.setSelected(false);
					unitHdoz.setSelected(false);
					unitPcs.setSelected(false);
					quantityField.clear();
					autosearch = true;
				}
			}
			
		});
		
		Platform.runLater(() -> barcodeField.requestFocus());
	}
	
	@FXML
	public void viewProducts() {
		getSceneManager().loadScene("viewProducts");
	}
	
	@FXML
	public void add() {
	    if (StringUtils.isEmpty(quantityField.getText())) {
	        ShowDialog.error("Quantity is required");
	        quantityField.requestFocus();
	        return;
	    }
	    
	    if (entriesTable.getItems().size() == MAX_ENTRIES) {
	        ShowDialog.error("Max entries reached");
	        return;
	    }
	    
		Entry entry = new Entry();
		entry.setProduct(productService.findByBarcode(barcodeField.getText()));
		entry.setUnit(getUnit());
		entry.setQuantity(Integer.valueOf(quantityField.getText()));
		
		entriesTable.getItems().add(entry);
		
		clear();
	}
	
	private String getUnit() {
		if (unitCase.isSelected()) return "CASE";
		if (unitTie.isSelected()) return "TIE";
		if (unitPck.isSelected()) return "PCK";
		if (unitHdoz.isSelected()) return "HDOZ";
		if (unitPcs.isSelected()) return "PCS";
		
		throw new RuntimeException("At least one unit should have been selected");
	}

	@FXML
	public void clear() {
		barcodeField.clear();
		descriptionField.clear();
		unitCase.setSelected(false);
		unitTie.setSelected(false);
		unitPck.setSelected(false);
		unitHdoz.setSelected(false);
		unitPcs.setSelected(false);
		quantityField.clear();
		barcodeField.requestFocus();
	}
	
	@FXML
	public void generateExcel() {
        FileChooser fileChooser = ExcelUtil.getSaveExcelFileChooser("inventory-monitoring-sheet.xlsx");
        File file = fileChooser.showSaveDialog(getSceneManager().getStage());
        if (file == null) {
            return;
        }
        
        try (
            Workbook workbook = new InventoryMonitoringSheetExcelGenerator().generate(entriesTable.getItems());
            FileOutputStream out = new FileOutputStream(file);
        ) {
            workbook.write(out);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            ShowDialog.unexpectedError();
            return;
        }
            
        ExcelUtil.openExcelFile(file);
	}
	
	@FXML
	public void clearEntries() {
	    entriesTable.getItems().clear();
	    clear();
	}
	
}
