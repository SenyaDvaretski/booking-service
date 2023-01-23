package com.hotelservice.hotelApi.controller;

import com.hotelservice.hotelApi.DTO.AdditionalServiceDTO;
import com.hotelservice.hotelApi.exception.CommonException;
import com.hotelservice.hotelApi.repository.HotelRepository;
import com.hotelservice.hotelApi.service.AdditionalServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/{hotel_name}/additional-services")
public class AdditionalServiceController extends BaseController{
    AdditionalServiceService additionalService;
    private final HotelRepository hotelRepository;

    public AdditionalServiceController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Operation(summary = "Get an additional service by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the additional service",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdditionalServiceDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Additional service not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<AdditionalServiceDTO>> getAdditionalService(@PathVariable(name = "hotel_name") String hotelName) throws Exception {
        return new ResponseEntity<>(additionalService.getAllAdditionalServices(hotelName), HttpStatus.OK);
    }

    @Operation(summary = "Get all additional services by hotel name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found more than zero additional services",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdditionalServiceDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Additional services not found",
                    content = @Content) })
    @GetMapping("/{additional_serviceName_name}")
    public ResponseEntity<AdditionalServiceDTO> getAdditionalService(@PathVariable(name = "hotel_name") String hotelName,
                                                                     @PathVariable(name = "additional_serviceName_name") String additionalServiceName) throws Exception {
        return new ResponseEntity<>(additionalService.getAdditionalService(hotelName, additionalServiceName), HttpStatus.OK);
    }

    @Operation(summary = "Add new additional service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully add additional services",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdditionalServiceDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Cannot add additional service, hotel not found",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<AdditionalServiceDTO> createAdditionalService(@PathVariable(name = "hotel_name") String hotelName,
                                   @RequestBody AdditionalServiceDTO additionalServiceDTO) throws CommonException {
        return new ResponseEntity<>(additionalService.addAdditionalService(hotelName, additionalServiceDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update additional service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated additional service",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdditionalServiceDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Hotel with this name is not found",
                    content = @Content) })
    @PutMapping
    public ResponseEntity<AdditionalServiceDTO> updateExcursion(@PathVariable(name = "hotel_name") String hotelName,
                                      @RequestBody AdditionalServiceDTO additionalServiceDTO) throws CommonException {
        return new ResponseEntity<>(additionalService.updateAdditionalService(hotelName, additionalServiceDTO), HttpStatus.OK);
    }

    @Operation(summary = "Enables or disables additional service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully enabled additional services",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdditionalServiceDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Hotel with this name is not found",
                    content = @Content) })
    @PatchMapping(path = "/{additional_serviceName_name}")
    public ResponseEntity<AdditionalServiceDTO> setExcursionEnabled(@PathVariable(name = "hotel_name") String hotelName,
                                      @PathVariable(name = "additional_serviceName_name") String additionalServiceName,
                                      @RequestBody Boolean isEnabled) throws CommonException {
        return new ResponseEntity<>(additionalService.setEnabled(hotelName, additionalServiceName, isEnabled), HttpStatus.OK);
    }

    @Operation(summary = "Delete additional service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted additional services",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdditionalServiceDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Hotel with this name is not found",
                    content = @Content) })
    @DeleteMapping(path = "/{additional_serviceName_name}")
    public ResponseEntity<AdditionalServiceDTO> deleteExcursion(@PathVariable(name = "hotel_name") String hotelName,
                                                   @PathVariable(name = "additional_serviceName_name") String additionalServiceName)
                                                    throws CommonException
    {
        return new ResponseEntity<>(additionalService.deleteAdditionalService(hotelName, additionalServiceName), HttpStatus.OK);
    }
}
