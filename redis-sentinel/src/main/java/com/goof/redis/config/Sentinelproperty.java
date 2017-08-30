package com.goof.redis.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author goofly
 *
 */

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.sentinel")
public class Sentinelproperty {

	List<String> nodes;
	
	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
}
