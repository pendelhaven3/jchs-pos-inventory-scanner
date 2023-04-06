package com.jchs.pos.inventoryscanner.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ControllerFactory implements Callback<Class<?>, Object>, ApplicationContextAware {

	private ApplicationContext context;
	
	@Override
	public Object call(Class<?> clazz) {
		return context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

}
