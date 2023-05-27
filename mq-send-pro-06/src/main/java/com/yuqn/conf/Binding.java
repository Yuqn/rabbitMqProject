package com.yuqn.conf;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yuqn
 * @Date: 2023/5/28 4:03
 * @description:
 * 绑定交换机
 * @version: 1.0
 */
@Configuration
public class Binding {
   /**
    * @author: yuqn
    * @Date: 2023/5/28 4:25
    * @description:
    * 创建交换机
    * @param: null
    * @return: null
    */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("springbootMqDirectExchange");
    }

    /**
     * @author: yuqn
     * @Date: 2023/5/28 4:25
     * @description:
     * 创建队列
     * @param: null
     * @return: null
     */
    @Bean
    public Queue directQueue(){
        return new Queue("springbootMqDirectQueue");
    }

    /**
     * @author: yuqn
     * @Date: 2023/5/28 4:23
     * @description:
     * 配置队列和交换机的判定
     * @param: directQueue 队列对象
     * @param: directExchange 交换机对象
     * @return: directBinding绑定对象
     */
    @Bean
    public org.springframework.amqp.core.Binding directBinding(Queue directQueue, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue).to(directExchange).with("springbootMqDirectExchangeRoutingKey");
    }
}
