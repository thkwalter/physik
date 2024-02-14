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
package de.thkwalter.galileitransformation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;

import static javax.measure.MetricPrefix.KILO;
import static javax.measure.MetricPrefix.MILLI;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.units.indriya.unit.Units.METRE;
import static tech.units.indriya.unit.Units.SECOND;

/**
 * Diese Klasse enthält JUnit-Tests für die Klasse {@link EreignisHelper}.
 */
class EreignisHelperTest
{
@Test
@DisplayName("Ereignis wird korrekt erzeugt")
void testErzeugeEreignis()
   {
   // Die Testdaten werden initialisiert
   double tMasszahl = 4.2;
   double xMasszahl = -2.5;
   Unit<Length> xEinheit = Units.METRE;
   Unit<Time> tEinheit = MILLI(SECOND);

   // Aus den Rohdaten werden Quantity-Objekte erzeugt.
   Quantity<Time> sollWertT = QuantityHelper.createTimeQuantity(tMasszahl, tEinheit);
   Quantity<Length> sollWertX = QuantityHelper.createLengthQuantity(xMasszahl, xEinheit);

   // Die zu testende Methode wird aufgerufen.
   Ereignis ereignis = EreignisHelper.erzeugeEreignis(tMasszahl, tEinheit, xMasszahl, xEinheit);

   // Es wird geprüft, ob das Ereignis die richtigen Werte für die Orts- und Zeitkoordinate enthält.
   QuantityHelper.compareQuantities(sollWertT, ereignis.t(), 1E-9, 1E-9);
   QuantityHelper.compareQuantities(sollWertX, ereignis.x(), 1E-9, 1E-9);
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Ereignisse werden korrekt verglichen")
void testCompareEreignisse()
   {
   // Die Testdaten werden intialisiert.
   Ereignis sollEreignis = EreignisHelper.erzeugeEreignis(2.2, SECOND, 0.0, KILO(METRE));
   Ereignis istEreignisKorrekt = EreignisHelper.erzeugeEreignis(2200, MILLI(SECOND), 0.0, METRE);
   Ereignis istEreignisFalsch1 = EreignisHelper.erzeugeEreignis(2.2001, SECOND, 0.0, KILO(METRE));
   Ereignis istEreignisFalsch2 = EreignisHelper.erzeugeEreignis(2.2, SECOND, 0.001, KILO(METRE));

   // Die zu testende Methode wird aufgerufen
   assertTrue(EreignisHelper.compareEreignisse(sollEreignis, istEreignisKorrekt,1E-9, 1E-9, 1E-9, 1E-9));
   assertFalse(EreignisHelper.compareEreignisse(sollEreignis, istEreignisFalsch1,1E-9, 1E-9, 1E-9, 1E-9));
   assertFalse(EreignisHelper.compareEreignisse(sollEreignis, istEreignisFalsch2,1E-9, 1E-9, 1E-9, 1E-9));
   }
}