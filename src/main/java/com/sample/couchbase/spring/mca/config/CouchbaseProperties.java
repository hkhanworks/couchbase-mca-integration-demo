package com.sample.couchbase.spring.mca.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author khanh
 *
 */
@Component
@Configuration
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@PropertySource(value="file:///${mca.home}/config/cbmca.properties", ignoreResourceNotFound=true)
@ConfigurationProperties(prefix="couchbase")
public class CouchbaseProperties {

	@NonNull
    private DbEntry app;
	
	@NonNull
    private DbEntry config;
	
	@NonNull
	private List<ClusterEntry> clusterEntries = new ArrayList<>();
	
	@NonNull
	private Long clusterGracePeriodMilliSeconds;

	@NonNull
    private Integer clusterActiveEntries;

	@Getter
    @Setter
    @ToString
    public static class DbEntry {
		
		@NonNull
		private String username;

		@NonNull
		private String bucket;

		@ToString.Exclude
		@NonNull
		private String password;

    }
	
	@Getter
    @Setter
    @ToString
    public static class ClusterEntry {

        private String identifier;
        private List<String> hostnames;
        private int priority;
    }
}
