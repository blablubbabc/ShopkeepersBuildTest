package com.nisovin.shopkeepers.compat.v1_16_R3;

import static org.junit.Assert.*;

import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.junit.Test;

import com.nisovin.shopkeepers.compat.CompatVersion;

public class MappingsVersionTest {

	@Test
	public void testMappingsVersion() throws Exception {
		NMSHandler nmsHandler = new NMSHandler();
		CompatVersion compatVersion = nmsHandler.getCompatVersion();
		String expectedMappingsVersion = compatVersion.getMappingsVersion();
		String actualMappingsVersion = ((CraftMagicNumbers) CraftMagicNumbers.INSTANCE).getMappingsVersion();
		assertEquals("Unexpected mappings version!",
				expectedMappingsVersion,
				actualMappingsVersion
		);
	}
}
