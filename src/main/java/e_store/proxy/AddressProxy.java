package e_store.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geocode", url = "${geocode.url}")
public interface AddressProxy {

    @GetMapping()
    String getGeoData(@RequestParam String apikey, @RequestParam String geocode, @RequestParam String results, @RequestParam String format);
}
