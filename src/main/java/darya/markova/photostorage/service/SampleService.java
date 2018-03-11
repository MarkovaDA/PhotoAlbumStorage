package darya.markova.photostorage.service;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

    /**
     * Example service method.
     *
     * @return random number.
     */
    public Long getNumber() {
        return Math.round(Math.random() * 1000);
    }
}
