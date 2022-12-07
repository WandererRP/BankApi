package com.roland.solva.rateupdater;

import com.roland.solva.dto.RUBUSD;
import com.roland.solva.dto.Root;
import com.roland.solva.dto.USDKZT;
import com.roland.solva.enums.CurrencyType;
import com.roland.solva.models.CurrencyRates;
import com.roland.solva.repositories.CurrencyRatesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Roland Pilpani 04.12.2022
 */

@Service
public class CurrencyRateUpdater {
    private final CurrencyRatesRepository currencyRatesRepository;

    @Value("${twelvedata.apikey}")
    private String apikey;

    public CurrencyRateUpdater(CurrencyRatesRepository currencyRatesRepository) {
        this.currencyRatesRepository = currencyRatesRepository;
    }

    @Scheduled(fixedRate = 86400000)//1day
    //@Scheduled(fixedRate = 20000)
    @Transactional
    public void updateCurrencyRates() throws InterruptedException {
        try {

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.twelvedata.com/quote?symbol=USD/KZT,RUB/USD&apikey=" + apikey;
            //String url = "https://api.twelvedata.com/quote?symbol=USD/KZT,RUB/USD&eod=true&apikey=" + apikey;

            Root response = restTemplate.getForObject(url, Root.class);

            USDKZT usdKzt = response.getUSDKZT();
            RUBUSD rubUsd = response.getRUBUSD();

            setAndSaveKZT(usdKzt);
            setAndSaveRUB(rubUsd);


        }
        catch (Exception e){
            e.printStackTrace();
            wait(600000);
            updateCurrencyRates();

        }

    }

    private void setAndSaveRUB(RUBUSD rubUsd) {

        double rubRate;
        if (!rubUsd.getIs_market_open()){
            rubRate = rubUsd.getClose();
        }
        else {
            rubRate = rubUsd.getPrevious_close();
        }
        CurrencyRates currencyRates = new CurrencyRates();
        currencyRates.setCurrency(CurrencyType.RUB);
        currencyRates.setRate(rubRate);

        Date date = rubUsd.getDatetime();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.of("UTC"));
        currencyRates.setDateTime(zonedDateTime);
        currencyRatesRepository.save(currencyRates);
    }

    private void setAndSaveKZT(USDKZT usdKzt) {

        double kztRate;
        if (!usdKzt.getIs_market_open()){
            kztRate = 1.0 / usdKzt.getClose();
        }
        else {
            kztRate = 1.0 / usdKzt.getPrevious_close();
        }

        CurrencyRates currencyRates = new CurrencyRates();
        currencyRates.setCurrency(CurrencyType.KZT);
        currencyRates.setRate(kztRate);

        Date date = usdKzt.getDatetime();

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.of("UTC"));
        currencyRates.setDateTime(zonedDateTime);

        currencyRatesRepository.save(currencyRates);

    }

}
