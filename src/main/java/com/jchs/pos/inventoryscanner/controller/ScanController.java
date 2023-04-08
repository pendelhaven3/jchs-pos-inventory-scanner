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
import javafx.scene.control.Button;
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
	@FXML private Button addButton;
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
						addButton.requestFocus();
					} else {
						ShowDialog.error("Barcode not found");
					}
					autosearch = false;
				} else {
					descriptionField.clear();
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
	    if (entriesTable.getItems().size() == MAX_ENTRIES) {
	        ShowDialog.error("Max entries reached");
	        return;
	    }
	    
	    Product product = productService.findByBarcode(barcodeField.getText());
	    
		Entry entry = new Entry();
		entry.setProduct(productService.findByBarcode(barcodeField.getText()));
		entry.setUnit(product.getUnit());
		
		entriesTable.getItems().add(entry);
		
		clear();
		autosearch = true;
		entriesTable.scrollTo(entriesTable.getItems().size()-1);		
	}
	
	@FXML
	public void clear() {
		barcodeField.clear();
		descriptionField.clear();
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
