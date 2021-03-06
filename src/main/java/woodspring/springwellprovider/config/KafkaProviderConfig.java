package woodspring.springwellprovider.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import org.springframework.kafka.support.serializer.JsonSerializer;

import woodspring.springwellprovider.entity.StockFeed;

import org.apache.kafka.common.serialization.StringSerializer;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class KafkaProviderConfig {
	final static int PARTITIONS = 3;
	final static short REPLICATION = 1;
	
	@Autowired
	private KafkaProperties kafkaProperties;
	
	@Value("${tpd.topic-name}")
	private String topicName;
	
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
	
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,  JsonSerializer.class);
		return props;
	}
	
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public NewTopic adviceTopic() {
		return new NewTopic(topicName, PARTITIONS, REPLICATION);
		
	}
	
	@Bean
    public ProducerFactory<String, StockFeed> stockFeedProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, StockFeed> stockFeedKafkaTemplate() {
        return new KafkaTemplate<>(stockFeedProducerFactory());
    }
	
	
	

}
