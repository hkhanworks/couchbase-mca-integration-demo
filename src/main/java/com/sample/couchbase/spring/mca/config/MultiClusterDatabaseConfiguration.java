/*
 * Copyright 2019 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.couchbase.spring.mca.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.CouchbaseConfigurer;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.mc.ClusterSpec;
import com.couchbase.client.mc.MultiClusterClient;
import com.couchbase.client.mc.coordination.Coordinator;
import com.couchbase.client.mc.coordination.Coordinators;
import com.couchbase.client.mc.coordination.IsolatedCoordinator;
import com.couchbase.client.mc.coordination.IsolatedCoordinator.Options;
import com.couchbase.client.mc.coordination.TopologyBehavior;
import com.couchbase.client.mc.detection.FailureDetector;
import com.couchbase.client.mc.detection.FailureDetectorFactory;
import com.couchbase.client.mc.detection.FailureDetectors;
import com.couchbase.client.mc.detection.NodeHealthFailureDetector;
import com.sample.couchbase.spring.mca.config.internal.AbstractMultiClusterConfiguration;
import com.sample.couchbase.spring.mca.config.internal.SpringMcaBucket;

@Configuration
public class MultiClusterDatabaseConfiguration extends AbstractMultiClusterConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MultiClusterDatabaseConfiguration.class);
	
	@Autowired
	protected CouchbaseProperties couchbaseProperties;
	
	protected CouchbaseConfigurer couchbaseConfigurer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public MultiClusterClient multiClusterClient() {
		MultiClusterClient client = new MultiClusterClient(coordinator(), failureDetectorFactory(), couchbaseEnvironment());
		client.authenticate(userName(), userPass());
		return client;
	}	
	
	@Bean
	public Bucket multiClusterBucket() {
	    return new SpringMcaBucket(multiClusterClient(), bucketName(), couchbaseEnvironment());
	}
	
	@Bean
	public CouchbaseEnvironment couchbaseEnvironment() {
		return super.couchbaseEnvironment();
	}

	@Bean
	@Override
	public String bucketName() {
		return couchbaseProperties.getApp().getBucket();
	}

	@Override
	public String userName() {
		return couchbaseProperties.getApp().getUsername();
	}

	@Override
	public String userPass() {
		return couchbaseProperties.getApp().getPassword();
	}

	@Bean
	public FailureDetectorFactory<? extends FailureDetector> failureDetectorFactory() {
		return FailureDetectors.nodeHealth(coordinator(), NodeHealthFailureDetector.options());
	}
	
    @Bean
    public Coordinator coordinator() {
    	
    	Set<ServiceType> serviceTypes = new HashSet<>();
        serviceTypes.add(ServiceType.BINARY);
        serviceTypes.add(ServiceType.QUERY);
    	
        logger.info("Creating Couchbase Cluster Coordinator - ClusterEntries (List): {} ",  couchbaseProperties.getClusterEntries());
        
        List<ClusterSpec> specs = new ArrayList<ClusterSpec>();
        for (CouchbaseProperties.ClusterEntry entry : couchbaseProperties.getClusterEntries()) {
        	logger.info("{}", entry);
            specs.add(ClusterSpec.create(
                new HashSet<>(entry.getHostnames()),
                entry.getIdentifier(),
                entry.getPriority()
            ));
        }

        Options options = new IsolatedCoordinator.Options()
            .topologyBehavior(TopologyBehavior.WRAP_AT_END)
            .clusterSpecs(specs);
            
        return Coordinators.isolated(options);
    }

}
