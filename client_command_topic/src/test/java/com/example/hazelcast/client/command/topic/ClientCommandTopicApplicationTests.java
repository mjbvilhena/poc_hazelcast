package com.example.hazelcast.client.command.topic;

import com.example.hazelcast.shared.model.Vehicle;
import com.example.hazelcast.shared.model.VehicleDetails;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import javassist.tools.rmi.Sample;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientCommandTopicApplicationTests implements  MessageListener<Vehicle> {

	private static LocalDateTime currentDate;
	private Vehicle vehicle;
	private Vehicle vehicleFromTopic;

	@Before
	public void init() throws Exception {
		currentDate = LocalDateTime.now();
	}

    @Test
	public void testTopicIsLoad() throws InterruptedException {
		HazelcastInstance client = HazelcastClient.newHazelcastClient();
        CountDownLatch latch = new CountDownLatch(1);
        ClientCommandTopicApplicationTests cientCommandTopicApplicationTests = new ClientCommandTopicApplicationTests();
		ITopic<Vehicle> topic = client.getTopic("Vehicle_Topic");
		topic.addMessageListener(cientCommandTopicApplicationTests);

//        String messageListener = topic.addMessageListener(message -> {
//            latch.countDown();
//        });

        //OR
        String messageListener = topic.addMessageListener(new MessageListener<Vehicle>() {
            public void onMessage(Message<Vehicle> msg) {
                latch.countDown();
            }
        });

        try{
            topic.publish(new Vehicle(currentDate, new VehicleDetails("1","White","BMW")));
            assertTrue(latch.await(1000, TimeUnit.MILLISECONDS));//CHECK is receive messages

        }catch (Exception e){
            e.printStackTrace();

        }

	}

    @Override
    public void onMessage(Message<Vehicle> message) {
        vehicleFromTopic = message.getMessageObject();
        System.out.println( "Message received = " + vehicleFromTopic.getRegistrationDate());
    }



}
