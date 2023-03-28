package com.high_con.grad.rab_m;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RaConfig {

    public static final String Sel_Queue = "sel.que";
    public static final String Kill_Queue = "kill.que";
    public static final String Queue_Name = "que";

    public static final String TOPP_Queue1 = "t.que1";

    public static final String TOPP_Queue2 = "t.que2";

    public static final String HEAD_Queue = "h.que1";

    /*public static final String ROUT_KEY1 = "t.key1";

    public static final String ROUT_KEY2 = "t.#";*/
    public static final String TOPP_EX = "topp_ex";

    public static final String FANOUT_EX = "fanout_ex";

    public static final String HEADERS_EX ="headers_ex";

    @Bean
    public Queue queue(){
        return new Queue(Queue_Name,true);
    }

    @Bean
    public Queue queue_kill(){
        return new Queue(Kill_Queue,true);
    }

    @Bean
    public Queue queue_sel(){
        return new Queue(Sel_Queue,true);
    }

    @Bean
    public Queue t_que1(){
        return new Queue(TOPP_Queue1,true);
    }

    @Bean
    public Queue t_que2(){
        return new Queue(TOPP_Queue2,true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPP_EX);
    }

    @Bean
    public Binding top_Bind1(){
        return BindingBuilder.bind(t_que1()).to(topicExchange()).with("t.key1");
    }


    @Bean
    public Binding top_Bind2(){
        return BindingBuilder.bind(t_que2()).to(topicExchange()).with("t.#");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EX);
    }

    @Bean
    public Binding fanoutbind1(){
        return BindingBuilder.bind(t_que1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutbind2(){
        return BindingBuilder.bind(t_que2()).to(fanoutExchange());
    }


    //Header
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EX);
    }


    @Bean
    public Queue header_que1(){
        return new Queue(HEAD_Queue,true);
    }


    @Bean
    public Binding headbind(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("head1","val1");
        map.put("head2","val2");
        return BindingBuilder.bind(header_que1()).to(headersExchange()).whereAll(map).match();
    }
}
