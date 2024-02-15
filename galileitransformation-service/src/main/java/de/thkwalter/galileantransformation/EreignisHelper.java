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

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;

public class EreignisHelper
{
/**
 * Diese Methode erzeugt aus einer Zeit- und einer Ortskoordniate ein {@link Event}.
 *
 * @param tMasszahl die Maßzahl der Zeitkoordinate
 * @param tEinheit  die Einheit der Zeitkoordinate
 * @param xMasszahl die Maßzahl der Ortskoordinate
 * @param xEinheit  die Einheit der Ortskoordinate
 * @return das aus der Zeit- und der Ortskoordinate gebildete {@link Event}.
 */
public static Event erzeugeEreignis(double tMasszahl, Unit<Time> tEinheit, double xMasszahl, Unit<Length> xEinheit)
   {
   // Die Quantity-Objekte der beiden Koordinaten werden erzeugt.
   Quantity<Time> t = QuantityUtils.createTimeQuantity(tMasszahl, tEinheit);
   Quantity<Length> x = QuantityUtils.createLengthQuantity(xMasszahl, xEinheit);

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
 * @param sollEvent        das erwartete Ereignis
 * @param istEvent         das vorhandene Ereignis
 * @param relativeAbweichungX die zulässige, relative Abweichung bei der Ortskoordinate
 * @param absoluteAbweichungX die zulässige absolute Abweichung (falls einer der Werte gleich null ist) bei der
 *                            Ortskoordinate
 * @param relativeAbweichungT die zulässige, relative Abweichung bei der zeitkoordinate
 * @param absoluteAbweichungT die zulässige absolute Abweichung (falls einer der Werte gleich null ist) bei der
 *                            Zeitkoordinate
 * @return <tt>true</tt>, falls beide Ereignisse im Rahmen der gegebenen Toleranzbereiche übereinstimmen;
 * <tt>false</tt> sonst
 */
public static boolean compareEreignisse(Event sollEvent, Event istEvent, double relativeAbweichungX,
      double absoluteAbweichungX, double relativeAbweichungT, double absoluteAbweichungT)
   {

   // Die Zeit- und die Ortskoordinaten werden verglichen.
   return QuantityUtils.compareQuantities(sollEvent.x(), istEvent.x(), relativeAbweichungX,
         absoluteAbweichungX) && QuantityUtils.compareQuantities(sollEvent.t(), istEvent.t(),
         relativeAbweichungT, absoluteAbweichungT);
   }
}
