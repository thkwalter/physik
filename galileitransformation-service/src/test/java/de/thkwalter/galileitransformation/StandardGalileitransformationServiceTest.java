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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;

/**
 * Tests für die Klasse {@link StandardGalileitransformationService}.
 * 
 * @author Th. K. Walter
 */
class StandardGalileitransformationServiceTest
{
/**
 * Test für die Methode
 * {@link StandardGalileitransformationService#transformiere(double, double, double)}. Der Test prüft
 * nach, ob die Funktionsargumente korrekt an die {@link StandardGalileitransformation} übergeben werden.
 */
@DisplayName("Die Funktionsargumente werden korrekt an die StandardGalileitransformation übergeben.")
@Test
void testTransformiere()
   {
   // Ein Objekt der zu testenden Klasse wird erzeugt.
   StandardGalileitransformationService controller = new StandardGalileitransformationService();
   
   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = controller.transformiere(-2.0, 3.0, 1.0);
   
   // Anhand des transformierten Ereignisses wird geprüft, ob die Funktionsargumente korrekt verarbeitet werden.
   Quantity<Time> tQuantity = transformiertesEreignis.t();
   double t = tQuantity.getValue().doubleValue();
   Unit<Time> tUnit = tQuantity.getUnit();
   assertEquals(-2.0, t, 2.0 * 1E-9);
   assertEquals(Units.SECOND, tUnit);

   Quantity<Length> xQuantity = transformiertesEreignis.x();
   double x = xQuantity.getValue().doubleValue();
   Unit<Length> xUnit = xQuantity.getUnit();
   assertEquals(5.0, x, 5.0 * 1E-9);
   assertEquals(Units.METRE, xUnit);
   }
}
