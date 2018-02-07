package com.example.hazelcast.client.command.topic;

import com.example.hazelcast.shared.model.Vehicle;
import com.example.hazelcast.shared.model.VehicleDetails;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientCommandTopicApplicationTests {

	private static LocalDateTime currentDate;

	@Before
	public void init() throws Exception {
		currentDate = LocalDateTime.now();
	}

    @Test
	public void testTopicIsLoad() throws InterruptedException {
		HazelcastInstance client = HazelcastClient.newHazelcastClient();
        CountDownLatch latch = new CountDownLatch(1);
        ListenerTopicTest listenerTopicTest = new ListenerTopicTest();
		ITopic<Vehicle> topic = client.getTopic("Vehicle_Topic");
		String message = topic.addMessageListener(listenerTopicTest);

        try{
            topic.publish(new Vehicle(currentDate, new VehicleDetails("1","White","BMW")));

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            if(null != message && null != topic){
                topic.removeMessageListener(message);
            }
        }

	}


}
