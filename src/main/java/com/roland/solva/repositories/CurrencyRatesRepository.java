package com.roland.solva.repositories;

import com.roland.solva.enums.CurrencyType;
import com.roland.solva.models.CurrencyRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRatesRepository extends JpaRepository<CurrencyRates, CurrencyType> {


}