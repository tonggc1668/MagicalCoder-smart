package com.magicalcoder.youyamvc.core.common.utils.copy;

import com.opensymphony.xwork.util.OgnlUtil;
import com.opensymphony.xwork.util.XWorkConverter;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
@Deprecated
public class OgnlCopyer {

	private static final Log log = LogFactory.getLog(OgnlUtil.class);

	private static HashMap expressions = new HashMap();

	private static HashMap beanInfoCache = new HashMap();
	
	


	/**
	 * Wrapper around Ognl.setValue() to handle type conversion for collection
	 * elements. Ideally, this should be handled by OGNL directly.
	 */
	static void setValue(String name, Map context, Object root,
			Object value) throws OgnlException {
		Ognl.setValue(compile(name), context, root, value);
	}

	static Object compile(String expression) throws OgnlException {
		synchronized (expressions) {
			Object o = expressions.get(expression);

			if (o == null) {
				o = Ognl.parseExpression(expression);
				expressions.put(expression, o);
			}

			return o;
		}
	}

	// ***************
	public static void copy(Object from, Object to) {
		copy(from, to, false, false);
	}
 
	public static void copy(Object from, Object to, boolean useProxy,
			boolean deepProxy) {
		if (from == null || to == null) {
			log
					.warn("Attempting to copy from or to a null source. This is illegal and is bein skipped. This may be due to an error in an OGNL expression, action chaining, or some other event.");

			return;
		}

		Map contextFrom = Ognl.createDefaultContext(from);
		Ognl.setTypeConverter(contextFrom, XWorkConverter.getInstance());
		Map contextTo = Ognl.createDefaultContext(to);
		Ognl.setTypeConverter(contextTo, XWorkConverter.getInstance());

		PropertyDescriptor[] fromPds;
		PropertyDescriptor[] toPds;

		try {
			fromPds = getPropertyDescriptors(from);
			toPds = getPropertyDescriptors(to);
		} catch (IntrospectionException e) {
			log.error("An error occured", e);

			return;
		}

		Map toPdHash = new HashMap();

		for (int i = 0; i < toPds.length; i++) {
			PropertyDescriptor toPd = toPds[i];
			toPdHash.put(toPd.getName(), toPd);
		}

		for (int i = 0; i < fromPds.length; i++) {
			PropertyDescriptor fromPd = fromPds[i];
			if (fromPd.getReadMethod() != null) {
				PropertyDescriptor toPd = (PropertyDescriptor) toPdHash
						.get(fromPd.getName());
				if ((toPd != null) && (toPd.getWriteMethod() != null)) {
					try {
						copyProperty(from, to, useProxy, deepProxy, fromPd, toPd, contextFrom, contextTo);
					} catch (Exception e) {
						// ignore
					}
				}

			}

		}
	}

	private static void copyProperty(Object from, Object to, boolean useProxy,
			boolean deepProxy, PropertyDescriptor fromPd,
			PropertyDescriptor toPd, Map contextFrom, Map contextTo)
			throws OgnlException , ClassNotFoundException {
		Object expr = OgnlUtil.compile(fromPd.getName());
		Object value = Ognl.getValue(expr, contextFrom, from);
			realCopyProperty(to, useProxy, contextTo, expr, value);

	}

	private static void realCopyProperty(Object to, boolean useProxy, Map contextTo,
			Object expr, Object fromValue)
			throws OgnlException {
		Ognl.setValue(expr, contextTo, to, fromValue);
	}

	static PropertyDescriptor[] getPropertyDescriptors(Object source)
			throws IntrospectionException {
		BeanInfo beanInfo = getBeanInfo(source);
		return beanInfo.getPropertyDescriptors();
	}

	static Map getBeanMap(Object source) throws IntrospectionException,
			OgnlException {
		Map beanMap = new HashMap();
		Map sourceMap = Ognl.createDefaultContext(source);
		PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(source);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
			String propertyName = propertyDescriptor.getDisplayName();
			Method readMethod = propertyDescriptor.getReadMethod();
			if (readMethod != null) {
				Object expr = OgnlUtil.compile(propertyName);
				Object value = Ognl.getValue(expr, sourceMap, source);
				beanMap.put(propertyName, value);
			} else {
				beanMap.put(propertyName, "There is no read method for "
						+ propertyName);
			}
		}
		return beanMap;
	}

	static BeanInfo getBeanInfo(Object from)
			throws IntrospectionException {
		synchronized (beanInfoCache) {
			BeanInfo beanInfo;
			beanInfo = (BeanInfo) beanInfoCache.get(from.getClass());
			if (beanInfo == null) {
				beanInfo = Introspector.getBeanInfo(from.getClass(),
						Object.class);
				beanInfoCache.put(from.getClass(), beanInfo);
			}
			return beanInfo;
		}
	}

	static void internalSetProperty(String name, Object value, Object o,
			Map context, boolean throwPropertyExceptions) {
		try {
			setValue(name, context, o, value);
		} catch (OgnlException e) {
			Throwable reason = e.getReason();
			String msg = "Caught OgnlException while setting property '" + name
					+ "' on type '" + o.getClass().getName() + "'.";
			Throwable exception = (reason == null) ? e : reason;

			if (throwPropertyExceptions) {
				log.error(msg, exception);
				throw new RuntimeException(msg);
			} else {
				log.warn(msg, exception);
			}
		}
	}
	
}
