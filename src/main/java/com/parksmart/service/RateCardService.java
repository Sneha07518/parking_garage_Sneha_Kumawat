package com.parksmart.service;

import java.util.*; import com.fasterxml.jackson.databind.JsonNode; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import com.parksmart.entity.*; import com.parksmart.repository.RateCardRepository;

@Service public class RateCardService {
    private final RateCardRepository cards; private final ParkingService parking; private final ClockService clock;
    public RateCardService(RateCardRepository c,ParkingService p,ClockService clock){cards=c;parking=p;this.clock=clock;}
    public List<RateCard> find(long garageId){parking.garage(garageId);return cards.findByGarageId(garageId);}
    @Transactional public RateCardParser.Result importText(long garageId,String text){Garage garage=parking.garage(garageId);RateCardParser.Result result=RateCardParser.parse(text);for(RateCardParser.Row row:result.imported()){RateCard card=cards.findByGarageIdAndSpotType(garageId,row.spotType()).orElseGet(RateCard::new);card.setGarage(garage);card.setSpotType(row.spotType());card.setFirstHourRate(row.firstHourRate());card.setExtraHourRate(row.extraHourRate());card.setDailyCap(row.dailyCap());card.setUpdatedAt(clock.now());cards.save(card);}return result;}
    public RateCardParser.Result importRows(long garageId,JsonNode body){StringBuilder csv=new StringBuilder("type,first,extra,cap\n");for(JsonNode row:body)csv.append(row.path("type").asText(row.path("spotType").asText())).append(',').append(row.path("first").asText(row.path("firstHourRate").asText())).append(',').append(row.path("extra").asText(row.path("extraHourRate").asText())).append(',').append(row.path("cap").asText(row.path("dailyCap").asText())).append('\n');return importText(garageId,csv.toString());}
    public RateCard rateFor(Spot spot){return cards.findByGarageIdAndSpotType(spot.getGarage().getId(),spot.getType()).orElse(null);}
}