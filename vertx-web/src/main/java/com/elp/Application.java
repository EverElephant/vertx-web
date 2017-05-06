package com.elp;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Application {

	public static void main(String[] args) {
		VertxOptions vo = new VertxOptions();
		vo.setEventLoopPoolSize(16);
		Vertx vertx = Vertx.vertx(vo);
		
		

	}

}
