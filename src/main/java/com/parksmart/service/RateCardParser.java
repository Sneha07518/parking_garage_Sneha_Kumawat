package com.parksmart.service;

import java.math.BigDecimal; import java.util.*; import com.parksmart.entity.SpotType;

public final class RateCardParser {
    private RateCardParser() {}
    public record Row(SpotType spotType,BigDecimal firstHourRate,BigDecimal extraHourRate,BigDecimal dailyCap) {}
    public record Rejected(int line,String raw,String reason) {}
    public record Result(List<Row> imported,List<Rejected> rejected,List<String> warnings) {}
    public static Result parse(String input) {
        List<Row> imported=new ArrayList<>(); List<Rejected> rejected=new ArrayList<>(); List<String> warnings=new ArrayList<>(); int typeIndex=-1,firstIndex=-1,extraIndex=-1,capIndex=-1;
        String[] lines=input==null?new String[0]:input.split("\\R",-1); boolean headerFound=false;
        for(int i=0;i<lines.length;i++){String raw=lines[i];String text=raw.trim();if(text.isEmpty()||text.startsWith("#"))continue;String[] cells=split(raw);
            if(!headerFound){Map<String,Integer> headers=new HashMap<>();for(int j=0;j<cells.length;j++)headers.put(normal(cells[j]),j);if(!headers.containsKey("type")||!headers.containsKey("first")||!headers.containsKey("extra")||!headers.containsKey("cap")){rejected.add(new Rejected(i+1,raw,"missing required header"));continue;}typeIndex=headers.get("type");firstIndex=headers.get("first");extraIndex=headers.get("extra");capIndex=headers.get("cap");headerFound=true;continue;}
            if(isHeader(cells)){continue;} try {Row row=clean(cells,typeIndex,firstIndex,extraIndex,capIndex);if(imported.stream().anyMatch(x->x.spotType()==row.spotType())){warnings.add("line "+(i+1)+" "+row.spotType()+" overridden");imported.removeIf(x->x.spotType()==row.spotType());}imported.add(row);}catch(IllegalArgumentException e){rejected.add(new Rejected(i+1,raw,e.getMessage()));}
        } return new Result(imported,rejected,warnings);
    }
    private static Row clean(String[] c,int typeIndex,int firstIndex,int extraIndex,int capIndex){SpotType spot=type(value(c,typeIndex));BigDecimal first=money(value(c,firstIndex)),extra=money(value(c,extraIndex)),cap=money(value(c,capIndex));if(first.signum()<0||extra.signum()<0||cap.signum()<0)throw new IllegalArgumentException("negative money value");if(extra.compareTo(first)>0)throw new IllegalArgumentException("extraHourRate greater than firstHourRate");if(cap.compareTo(first)<0)throw new IllegalArgumentException("dailyCap less than firstHourRate");return new Row(spot,first,extra,cap);}
    private static String value(String[] c,int i){if(i<0||i>=c.length||c[i].trim().isEmpty())throw new IllegalArgumentException("missing value");return c[i].trim();}
    private static BigDecimal money(String s){if(s.equalsIgnoreCase("free"))return BigDecimal.ZERO;String v=s.replaceAll("(?i)rs\\.?|inr|₹|\\$|/hr|[,\\s]","");try{return new BigDecimal(v);}catch(Exception e){throw new IllegalArgumentException("non-numeric money value");}}
    private static SpotType type(String s){String v=s.toLowerCase(Locale.ROOT).replaceAll("[^a-z]","");return switch(v){case "ev","electric","evcharger"->SpotType.EV;case "compact","small","cmp"->SpotType.COMPACT;case "standard","std","regular","normal"->SpotType.STANDARD;default->throw new IllegalArgumentException("unknown spot type");};}
    private static String normal(String s){String v=s.trim().toLowerCase(Locale.ROOT).replaceAll("[ _-]","");return switch(v){case "type","spottype"->"type";case "first","firsthour","firsthourrate"->"first";case "extra","additional","perhour","extrahour","extrahourrate"->"extra";case "cap","daily","dailycap","max"->"cap";default->v;};}
    private static boolean isHeader(String[] c){return c.length>0&&normal(c[0]).equals("type")&&c.length>1&&normal(c[1]).equals("first");}
    private static String[] split(String line){return line.split(",",-1);}
}