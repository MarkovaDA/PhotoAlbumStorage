package darya.markova.photostorage.util;

import java.util.UUID;

/**
 * UUID generator.
 */
public class UuidGenerator {

    /**
     * Generates random UUID.
     * @return generated UUID.
     */
    public static UUID generate() {
        return UUID.randomUUID();
    }
}
