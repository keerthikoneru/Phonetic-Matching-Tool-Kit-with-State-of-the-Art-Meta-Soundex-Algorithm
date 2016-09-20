package PhoneticMatching;

import PhoneticMatching.EncodeException;
import PhoneticMatching.StrEncoder;

public abstract class AbsCaverphone implements StrEncoder {

    /**
     * Creates an instance of the encoder
     */
    public AbsCaverphone() {
        super();
    }

    /**
     * If the provided object is not String type then an Encoder Exception is thrown
     */
    /* (non-Javadoc)
     * @see PhoneticMatching.Encode#encode(java.lang.Object)
     */
    public Object encode(final Object input) throws EncodeException {
        if (!(input instanceof String)) {
            throw new EncodeException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.encode((String) input);
    }
}
