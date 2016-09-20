package PhoneticMatching;

import PhoneticMatching.EncodeException;

public interface Encode {

    /**
     *
     * @param input
     * @return An "encoded" Object
     * @throws EncoderException
     *             
     */
    Object encode(Object input) throws EncodeException;
}
