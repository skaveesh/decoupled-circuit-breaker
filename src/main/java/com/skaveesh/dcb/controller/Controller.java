package com.skaveesh.dcb.controller;

import com.skaveesh.dcb.service.DownstreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class Controller {

  private final DownstreamService downstreamService;

  @Autowired
  public Controller(final DownstreamService downstreamService){
    this.downstreamService = downstreamService;
  }

  @GetMapping(value = "calltest", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> callTest(){
    return new ResponseEntity<>(downstreamService.callTest(), HttpStatus.OK);
  }

  @GetMapping(value = "calltestslow", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> callTestSlow(){
    return new ResponseEntity<>(downstreamService.callTestSlow(), HttpStatus.OK);
  }

  @GetMapping(value = "calltesterror", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> callTestError(){
    return new ResponseEntity<>(downstreamService.callTestError(), HttpStatus.OK);
  }

}
