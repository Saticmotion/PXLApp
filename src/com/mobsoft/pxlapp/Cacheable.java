package com.mobsoft.pxlapp;

/**
 * Deze interface zorgt ervoor dat een klasse gemakkelijk omgezet kan worden in een bestand in cache, via de CacheManager.
 * @author simon
 *
 */
public interface Cacheable <T>
{
	/**
	 * Hiermee zet je je object om in tekst.
	 * @return
	 */
	String toCacheString();

	/**
	 * Dit dient om de bestandsnaam voor het cache-object in te stellen. Je kan bijvoorbeeld een 
	 * bestandsnaam als "Lessenrooster [datum]" of "Leerkracht [naam]" laten genereren
	 * @return
	 */
	String getBestandsnaam();

	T toObject(String bestandsnaam);
}
