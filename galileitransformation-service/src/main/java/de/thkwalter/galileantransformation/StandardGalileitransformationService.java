/**
 * Copyright 2023 Th. K. Walter
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
package de.thkwalter.galileantransformation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.units.indriya.AbstractUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;

/**
 * Diese Klasse implementiert den Endpunkt des StandardGalileitransformation-Services.
 *
 * @author Th. K. Walter
 */
@RestController
public class StandardGalileitransformationService
{
/**
 * Transformiert das mittels der Request-Parameter spezifizierte Ereignis. Die Geschwindigkeit der
 * Standard-Galileitransformation wird mithilfe des letzten Request-Parameters bestimmt.
 *
 * @param tMasszahl die Maßzahl der Zeitkoordinate
 * @param tEinheit  die Einheit der Zeitkoordinate
 * @param xMasszahl die Maßzahl der Ortskoordinate
 * @param xEinheit  Die Einheit der Ortskoordinate
 * @param vMasszahl Die Maßzahl der Geschwindigkeit
 * @return Das transformierte {@link Event}
 */
@RequestMapping("/transformiere")
public Event transformiere(@RequestParam(value = "tMasszahl") double tMasszahl,
      @RequestParam(value = "tEinheit") String tEinheit, @RequestParam(value = "xMasszahl") double xMasszahl,
      @RequestParam(value = "xEinheit") String xEinheit, @RequestParam(value = "vMasszahl") double vMasszahl,
      @RequestParam(value = "vEinheit") String vEinheit)
   {
   // Die Einheiten werden bestimmt.
   Unit<Time> tUnit = AbstractUnit.parse(tEinheit).asType(Time.class);
   Unit<Length> xUnit = AbstractUnit.parse(xEinheit).asType(Length.class);
   Unit<Speed> vUnit = AbstractUnit.parse(vEinheit).asType(Speed.class);

   // Das originale Ereignis wird mithilfe der Request-Parameter erzeugt.
   Event originalEvent = EventUtils.createEvent(tMasszahl, tUnit, xMasszahl, xUnit);

   // Die Galileitransformation wird mithilfe des letzten Request-Parameters erzeugt.
   Quantity<Speed> v = QuantityUtils.createSpeedQuantity(vMasszahl, vUnit);
   GalileanBoost galileitransformation = new GalileanBoost(v);

   // Das originale Ereignis wird transformiert und das transformierte Ereignis zurückgegeben.
   return galileitransformation.transform(originalEvent);
   }
}
