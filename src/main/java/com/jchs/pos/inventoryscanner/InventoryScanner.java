package com.jchs.pos.inventoryscanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.jchs.pos.inventoryscanner.component.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
public class InventoryScanner extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
        ApplicationContext context = new SpringApplicationBuilder(InventoryScanner.class).headless(false).run();
        
		SceneManager sceneManager = context.getBean(SceneManager.class);
		sceneManager.setStage(stage);
		sceneManager.loadScene("viewProducts");
		
		stage.setTitle("JCHS POS Inventory Scanner v1.0.0-SNAPSHOT");
		stage.show();
	}
	
//	private void showMainScreen(Stage stage) throws Exception {
		/*
		StageController stageController = context.getBean(StageController.class);
		stageController.setStage(stage);
		stageController.showMainMenuScreen();
		stage.setResizable(true);
		stage.getIcons().add(new Image(HRApp.class.getClassLoader().getResourceAsStream("images/icon.png")));
		
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null) {
            splash.close();
        }
		*/
		

//		stage.getScene().getStylesheets().add("css/application.css");
//	}

}