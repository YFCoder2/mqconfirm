package cn.mq.confirm;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * AcknowledgeMode.NONE 自动确认，等效于 autoAck=true
 * 
 * AcknowledgeMode.MANUAL 手动确认，等效于 autoAck=false
 *
 */
@ComponentScan
public class App {
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);

		System.out.println("spring startup");

		TimeUnit.SECONDS.sleep(90);
		context.close();
	}
}
