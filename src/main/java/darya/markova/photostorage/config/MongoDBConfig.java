package darya.markova.photostorage.config;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import darya.markova.photostorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.boot.CommandLineRunner;



@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public  class MongoDBConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabaseName;

    @Value("${photostorage.mongodb.host.ip}")
    private String mongoHost;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            //userRepository.deleteAll();
            //userRepository.save(new User(3,"veronika_lukina", "abcd"));
            //userRepository.save(new User(4,"anatoly_karpov", "efgs"));
        };
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    /*@Override
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost);
    }*/

    @Override
    protected String getDatabaseName() {
        return mongoDatabaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(mongoHost);
    }
}
