package me.xdark.reflectionhooks.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import me.xdark.reflectionhooks.api.Hook;
import me.xdark.reflectionhooks.api.HooksFactory;
import org.junit.jupiter.api.Test;

public class TestConstructor {


	@Test
	public void testNewInstance()
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		HooksFactory factory = new DefaultHooksFactory();
		Constructor<Klass> constructor = Klass.class.getDeclaredConstructor(String.class);
		constructor.setAccessible(true);
		Hook hook = factory.createConstructorHook(Klass.class, constructor, (parent, handle, args) -> {
			args[0] = "World!";
			return parent.invoke(null, null, args);
		});
		hook.hook();
		String value = constructor.newInstance("Hello").value;
		assertEquals(value, "World!");
		hook.unhook();
		value = constructor.newInstance("Hello").value;
		assertEquals(value, "Hello");
	}

	private static class Klass {

		final String value;

		private Klass(String value) {
			this.value = value;
		}
	}
}
