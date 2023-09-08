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

import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;
import javax.measure.spi.QuantityFactory;
import javax.measure.spi.ServiceProvider;

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
   // Die Geschwindigkeit wird von einem double-Wert in eine Quantity überführt.
   ServiceProvider provider = ServiceProvider.current();
   QuantityFactory<Speed> speedFactory = provider.getQuantityFactory(Speed.class);
   Quantity<Speed> v = speedFactory.create(this.v, Units.METRE_PER_SECOND);

   Quantity<Length> x = originalEreignis.x();
   Quantity<Time> t = originalEreignis.t();

   // Bei einer Standard-Galileitransformation muss nur die x-Koordinate des transformierten Ereignisses berechnet
   // werden.
   Quantity<Length> xTransformiert = x.subtract(v.multiply(t).asType(Length.class));

   // Das transformierte Ereignis wird erstellt und zurückgegeben.
   return new Ereignis(t, xTransformiert);
   }
}
