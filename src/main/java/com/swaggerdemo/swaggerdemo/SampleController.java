package com.swaggerdemo.swaggerdemo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SampleController {

    List<Country> countryList = new ArrayList<>();

    // add - POST

    @ApiOperation(value = "Add new Country")
    @RequestMapping(value = "/addCountry/{countryCode}/{countryName}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "UnAuthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public String getSampleMessage(@PathVariable("countryCode") String countryCode, @PathVariable("countryName") String countryName) {
        Country country = new Country();
        country.setCountryCode(countryCode);
        country.setCountryName(countryName);
        countryList.add(country);
        return "Success! new country added to the list";
    }

    // get - GET

    @ApiOperation(value = "Get all Countries")
    @RequestMapping(value = "/getAllCountries", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public List<Country> getAllCountries() {
        return countryList;
    }

    // update - PUT

    @ApiOperation(value = "Update Country name")
    @RequestMapping(value = "/updateCountry/{oldCountryCode}/{newCountryName}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public String updateCountry(@PathVariable("oldCountryCode") String oldCountryCode, @PathVariable("newCountryName") String newCountryName) {
        if(countryList.stream().filter(list -> oldCountryCode.equals(list.getCountryCode())).findAny().isPresent()) {
            countryList.stream()
                    .filter(list -> oldCountryCode.equals(list.getCountryCode()))
                    .forEach(list -> list.setCountryName(newCountryName));
            return "Updated Country name successfully!";
        } else {
            return "Country does not exist to update";
        }
    }

    // delete - DELETE

    @ApiOperation(value = "Delete Country")
    @RequestMapping(value = "/deleteCountry/{countryCode}", method = RequestMethod.DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public String deleteCountry(@PathVariable("countryCode") String countryCode) {
        boolean result = countryList.removeIf(list -> countryCode.equals(list.getCountryCode()));
        if(result)
            return "Deleted country successfully!";
        else
            return "Country does not exist to delete";
    }

}
