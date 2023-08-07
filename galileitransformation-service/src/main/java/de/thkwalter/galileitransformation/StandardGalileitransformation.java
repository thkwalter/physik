/**
 * Copyright 2019 Th. K. Walter
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *    http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.thkwalter.galileitransformation;

import javax.measure.Quantity;
import javax.measure.quantity.Time;

/**
 * Diese Klasse repräsentiert eine Galileitransformation für zwei Koordinatensysteme in der Standardkonfiguration.
 * 
 * @author Th. K. Walter
 */
public class StandardGalileitransformation
{
/**
 * Die Geschwindigkeit (in m/s)
 */
private final double v;

// =====================================================================================================================
// =====================================================================================================================

/**
 * Initialisiert die Galileitransformation durch Angabe der Geschwindigkeit.
 * 
 * @param v die Geschwindigkeit (in m/s)
 */
public StandardGalileitransformation(double v)
   {
   this.v = v;
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Transformiert ein Ereignis.
 * 
 * @param originalEreignis das originale Ereignis
 * 
 * @return das transformierte Ereignis
 */
public Ereignis transformiere(Ereignis originalEreignis)
   {
   double x = originalEreignis.x();
   
   // Die Zeitkoordinate wird in Sekunden gelesen.
   Quantity<Time> tQuantity = originalEreignis.t();
   double t = tQuantity.toSystemUnit().getValue().doubleValue();

   // Bei einer Standard-Galileitransformation muss nur die x-Koordinate des transformierten Ereignisses berechnet 
   // werden.
   double x_transformiert = x - v * t;

   // Das transformierte Ereignis wird erstellt und zurückgegeben.
   return new Ereignis(tQuantity, x_transformiert);
   }
}
