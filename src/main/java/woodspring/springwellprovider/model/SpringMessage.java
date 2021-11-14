package woodspring.springwellprovider.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpringMessage(
		@JsonProperty("symbol") String symbol,
		@JsonProperty("side") String side,
		@JsonProperty("price") Double price,
		@JsonProperty("createTime") Long createTime) {

}
