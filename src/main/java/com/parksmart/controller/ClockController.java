package com.parksmart.controller;

import java.time.*; import java.util.*; import org.springframework.web.bind.annotation.*; import com.parksmart.service.*;

@RestController public class ClockController {
    private final ClockService clock; private final AutoCloseService autoClose;
    public ClockController(ClockService c,AutoCloseService a){clock=c;autoClose=a;}
    @GetMapping("/clock") public Map<String,Object> get(){return Map.of("now",clock.now(),"simulated",clock.isSimulated());}
    @PostMapping("/clock") public Map<String,Object> set(@RequestBody(required=false) Map<String,Object> body){Instant now;if(body!=null&&body.get("now")!=null)now=clock.setNow(Instant.parse(body.get("now").toString()));else if(body!=null&&body.get("advanceHours")!=null)now=clock.advance(Duration.ofHours(((Number)body.get("advanceHours")).longValue()));else if(body!=null&&body.get("advanceMinutes")!=null)now=clock.advance(Duration.ofMinutes(((Number)body.get("advanceMinutes")).longValue()));else now=clock.now();return Map.of("now",now,"autoClosed",autoClose.runAutoClose(now));}
    @PostMapping("/clock/reset") public Map<String,Object> reset(){return Map.of("now",clock.reset());}
}