package e_store.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GeocodeResponse(
        @JsonProperty("response") Response response) {
    public record Response(@JsonProperty("GeoObjectCollection") GeoObjectCollection geoObjectCollection) {
    }

    public record GeoObjectCollection(
            @JsonProperty("metaDataProperty") MetaDataProperty metaDataProperty,
            @JsonProperty("featureMember") List<FeatureMember> featureMembers) {
        public record MetaDataProperty(
                @JsonProperty("GeocoderResponseMetaData") GeocoderResponseMetaData geocoderResponseMetaData) {
            public record GeocoderResponseMetaData(Integer found) {
            }
        }

        public record FeatureMember(
                @JsonProperty("GeoObject") GeoObject geoObject) {
            public record GeoObject(
                    @JsonProperty("metaDataProperty") AMetaDataProperty metaDataProperty) {
                public record AMetaDataProperty(@JsonProperty("GeocoderMetaData") GeocoderMetaData geocoderMetaData) {
                    public record GeocoderMetaData(@JsonProperty("Address") Address address) {
                        public record Address(String formatted) {
                        }
                    }
                }
            }
        }
    }
}
