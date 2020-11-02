package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Country;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	
	@Query("select country from Country country where country.countryName =:countryName")
	Optional<Country> findOneCountryName(@Param("countryName") String countryName);
}
