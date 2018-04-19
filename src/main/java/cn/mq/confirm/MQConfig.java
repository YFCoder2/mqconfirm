package cn.mq.confirm;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;

@Configuration
public class MQConfig {

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setUri("amqp://mqadmin:mqadmin@10.211.55.19:5672");
		return factory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbit = new RabbitAdmin(connectionFactory);
		return rabbit;
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbit = new RabbitTemplate(connectionFactory);
		return rabbit; 
	}
	
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames("log.debug");
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				System.out.println("+++++++++++++++onMessage++++++++++++++");
				System.out.println(new String(message.getBody()));
				TimeUnit.SECONDS.sleep(10);
//				if(message.getMessageProperties().getHeaders().get("error") == null) {
//					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//					System.out.println("消息已确认");
//				} else {
////					channel.basicNack(deliveryTag, multiple, requeue);
//					channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//					System.out.println("消息拒绝");
//				}
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

			}
		});
		return container;
	}

}
