/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.askdog.sync.configuration;

import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BatchDatabaseInitializer {

	@Autowired
	private BatchProperties properties;

	@Autowired
	private BatchDataSource dataSource;

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${askdog.batch.initializer.enabled}")
	private boolean initialize;

	@PostConstruct
	protected void initialize() {
		if (initialize) {
			String platform = getPlatform();
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			String schemaLocation = this.properties.getSchema();
			schemaLocation = schemaLocation.replace("@@platform@@", platform);
			populator.addScript(this.resourceLoader.getResource(schemaLocation));
			populator.setContinueOnError(true);
			DatabasePopulatorUtils.execute(populator, this.dataSource.getDataSource());
		}
	}

	private String getPlatform() {
		String platform = getDatabaseType();
		if ("hsql".equals(platform)) {
			platform = "hsqldb";
		}
		if ("postgres".equals(platform)) {
			platform = "postgresql";
		}
		if ("oracle".equals(platform)) {
			platform = "oracle10g";
		}
		return platform;
	}

	private String getDatabaseType() {
		try {
			return DatabaseType.fromMetaData(this.dataSource.getDataSource()).toString().toLowerCase();
		}
		catch (MetaDataAccessException ex) {
			throw new IllegalStateException("Unable to detect database type", ex);
		}
	}

}
