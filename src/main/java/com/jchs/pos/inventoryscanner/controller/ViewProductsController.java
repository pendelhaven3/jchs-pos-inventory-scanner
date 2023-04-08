package com.jchs.pos.inventoryscanner.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jchs.pos.inventoryscanner.component.ShowDialog;
import com.jchs.pos.inventoryscanner.model.Product;
import com.jchs.pos.inventoryscanner.service.ProductService;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

@Component
public class ViewProductsController extends AbstractController {

	@Autowired
	private ProductService productService;
	
	@FXML
	private TableView<Product> productsTable;
	
	@FXML
	public void initialize() {
		productsTable.getItems().setAll(productService.getAllProducts());
	}
	
    @FXML
    public void scan() {
        getSceneManager().loadScene("scan");
    }
    
	@FXML
	public void uploadProducts() {
	    FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Products File");
        fileChooser.setInitialDirectory(Paths.get(System.getProperty("user.home"), "Desktop").toFile());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV files", "*.csv"));
        
        File file = fileChooser.showOpenDialog(getSceneManager().getStage());
        if (file == null) {
            return;
        }
        
        List<Product> products = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(file);) {
            for (String line : IOUtils.readLines(fis, Charset.defaultCharset())) {
                String[] parts = line.split("\\|");
                
                if (parts.length < 3) {
                	ShowDialog.error("Missing UNIT column.\nPlease upload products file generated from jchs-pos version 2.6.8 or greater");
                	return;
                }
                
                String barcode = parts[0];
                String description = parts[1];
                String unit = parts[2];

                products.add(new Product(barcode, description, unit));
            }
        } catch (Exception e) {
            ShowDialog.unexpectedError();
            return;
        }
        
        productService.setProducts(products);
        productsTable.getItems().setAll(products);
	}
	
}
