package com.jchs.pos.inventoryscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.jchs.pos.inventoryscanner.component.SceneManager;

public abstract class AbstractController {

	@Autowired
	private SceneManager sceneManager;
	
	public SceneManager getSceneManager() {
		return sceneManager;
	}
	
}
