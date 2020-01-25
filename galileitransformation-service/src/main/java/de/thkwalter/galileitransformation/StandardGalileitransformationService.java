/**
 * Copyright 2019 Th. K. Walter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.thkwalter.galileitransformation;

import javax.measure.Quantity;
import javax.measure.quantity.Time;
import javax.measure.spi.QuantityFactory;
import javax.measure.spi.ServiceProvider;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.units.indriya.unit.Units;

/**
 * Diese Klasse implementiert den Endpunkt des StandardGalileitransformation-Services.
 * 
 * @author Th. K. Walter
 */
@RestController
public class StandardGalileitransformationService
{
/** Mit Hilfe dieser Factory lassen sich {@link Quantity}--Objekte für Zeitangaben erstellen */
private static QuantityFactory<Time> timeFactory;

// =====================================================================================================================
// =====================================================================================================================

/**
 * Initialisiert die Factory zur Erzeugung von {@link Quantity}--Objekten für Zeitangaben.
 */
public StandardGalileitransformationService()
   {
   ServiceProvider provider = ServiceProvider.current();
   StandardGalileitransformationService.timeFactory = provider.getQuantityFactory(Time.class);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Transformiert das mittels der Request-Parameter spezifizierte Ereignis. Die Geschwindigkeit der Standard-
 * Galileitransformation wird mit Hilfe des letzten Request-Parameters bestimmt. 
 * 
 * @param t Die Zeitkoordinate (in s)
 * @param x Die x-Koordinate (in m)
 * @param v Die Geschwindigkeit (in m/s)
 * 
 * @return Das transformierte {@link Ereignis}
 */
@RequestMapping("/transformiere")
public Ereignis transformiere(@RequestParam(value = "t") double t, @RequestParam(value = "x") double x,
   @RequestParam(value = "v") double v)
   {   
   // Das originale Ereignis wird mit Hilfe der Request-Parameter erzeugt.
   Quantity<Time> tQuantity = StandardGalileitransformationService.timeFactory.create(t, Units.SECOND);
   Ereignis originalEreignis = new Ereignis(tQuantity, x);
   
   // Die Galileitransformation wird mit Hilfe des letzten Request-Parameters erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(v);
   
   // Das originale Ereignis wird transformiert und das transformierte Ereignis zurückgegeben.
   return galileitransformation.transformiere(originalEreignis);
   }
}
