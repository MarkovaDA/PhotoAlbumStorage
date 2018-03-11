package darya.markova.photostorage.controller;

import darya.markova.photostorage.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    /**
     * {@link SampleService}
     */
    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    /**
     * Example controller method.
     *
     * @return random number.
     */
    @GetMapping(value = "/random/long")
    public ResponseEntity<Long> getRandomLong() {
        return new ResponseEntity<>(sampleService.getNumber(), HttpStatus.OK);
    }
}
