package darya.markova.photostorage.config;


import darya.markova.photostorage.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.boot.CommandLineRunner;



@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            //userRepository.deleteAll();
            //userRepository.save(new User(3,"veronika_lukina", "abcd"));
            //userRepository.save(new User(4,"anatoly_karpov", "efgs"));
        };
    }
}
