package com.github.edgger.adminconfigservice;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;

@SpringBootTest
@AutoConfigureEmbeddedDatabase(provider = ZONKY, type = POSTGRES)
class AdminConfigServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
