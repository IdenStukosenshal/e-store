package e_store.services;

import e_store.database.entity.Address;
import e_store.dto.in.GeocodeResponse;
import e_store.enums.ErrorCode;
import e_store.exceptions.LocalizedValidationException;
import e_store.proxy.AddressProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidateAddressService {

    private final String apiKey;
    private final AddressProxy addressProxy;

    public ValidateAddressService(AddressProxy addressProxy,
                                  @Value("${API_KEY}") String apiKey) {
        this.addressProxy = addressProxy;
        this.apiKey = apiKey;
    }

    public Address validAndCorrect(Address incAddress) {
        String geocodeRequest = incAddress.getCity() + " " + incAddress.getStreetAddress();
        GeocodeResponse geocodeResponse = addressProxy.getGeoData(apiKey, geocodeRequest, "3", "json");

        int found = geocodeResponse.response().geoObjectCollection().metaDataProperty().geocoderResponseMetaData().found();
        if (found == 0) throw new LocalizedValidationException(ErrorCode.ADDRESS_ON_MAP.getMsg());
        else if (found > 1) throw new LocalizedValidationException(ErrorCode.ADDRESS_MORE_THAN_ONE.getMsg());
        else {
            String result = geocodeResponse.response().geoObjectCollection().featureMembers().get(0).geoObject().metaDataProperty().geocoderMetaData().address().formatted();
            String[] resultMassive = result.split(", ");
            if (resultMassive.length < 3) throw new RuntimeException("Формат данных изменился");
            return new Address(null, resultMassive[1], result.substring(resultMassive[0].length() + resultMassive[1].length() + 4));
        }
    }
}
