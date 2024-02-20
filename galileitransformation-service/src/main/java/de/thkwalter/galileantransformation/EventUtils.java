/**
 * Copyright 2023 Th. K. Walter
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.thkwalter.galileantransformation;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;

/**
 * Diese Klasse enthält Methoden, um den Umgang mit {@link Event}--Objekten zu erleichtern.
 */
public class EventUtils
{
/**
 * Diese Methode erzeugt aus einer Zeit- und einer Ortskoordinate ein {@link Event}.
 *
 * @param tMeasure die Maßzahl der Zeitkoordinate
 * @param tUnit    die Einheit der Zeitkoordinate
 * @param xMeasure die Maßzahl der Ortskoordinate
 * @param xUnit    die Einheit der Ortskoordinate
 * @return das aus der Zeit- und der Ortskoordinate gebildete {@link Event}.
 */
public static Event createEvent(double tMeasure, Unit<Time> tUnit, double xMeasure, Unit<Length> xUnit)
   {
   // Die Quantity-Objekte der beiden Koordinaten werden erzeugt.
   Quantity<Time> t = QuantityUtils.createTimeQuantity(tMeasure, tUnit);
   Quantity<Length> x = QuantityUtils.createLengthQuantity(xMeasure, xUnit);

   // Das Ereignis wird erzeugt und zurückgegeben.
   return new Event(t, x);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Diese Methode vergleicht zwei {@link Event}-Objekte und gibt <tt>true</tt> zurück, falls die Zeit- und die
 * Ortskoordinaten (innerhalb gegebeber Toleranzbereiche) miteinander übereinstimmen. Zum Vergleich der einzelnen
 * Koordinatenwerte wird die Methode {@link QuantityUtils#compareQuantities(Quantity, Quantity, double, double)}
 * verwendet.
 *
 * @param expectedEvent       das erwartete Ereignis
 * @param actualEvent         das vorhandene Ereignis
 * @param relativeToleranceX  die zulässige, relative Abweichung bei der Ortskoordinate
 * @param absoluteToleranceX  die zulässige absolute Abweichung (falls einer der Werte gleich null ist) bei der
 *                            Ortskoordinate
 * @param relativeToleranceT  die zulässige, relative Abweichung bei der zeitkoordinate
 * @param absoluterToleranceT die zulässige absolute Abweichung (falls einer der Werte gleich null ist) bei der
 *                            Zeitkoordinate
 * @return <tt>true</tt>, falls beide Ereignisse im Rahmen der gegebenen Toleranzbereiche übereinstimmen;
 * <tt>false</tt> sonst
 */
public static boolean compareEvents(Event expectedEvent, Event actualEvent, double relativeToleranceX,
      double absoluteToleranceX, double relativeToleranceT, double absoluterToleranceT)
   {

   // Die Zeit- und die Ortskoordinaten werden verglichen.
   return QuantityUtils.compareQuantities(expectedEvent.x(), actualEvent.x(), relativeToleranceX,
         absoluteToleranceX) && QuantityUtils.compareQuantities(expectedEvent.t(), actualEvent.t(), relativeToleranceT,
         absoluterToleranceT);
   }
}
