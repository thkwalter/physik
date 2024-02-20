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
 * Diese Klasse enthält JUnit-Tests für die Klasse {@link EventUtils}.
 */
class EventUtilsTest
{
@Test
@DisplayName("Testet die Erzeugung eines Ereignisses")
void testCreateEvent()
   {
   // Die Testdaten werden initialisiert
   double tMeasure = 4.2;
   double xMeasure = -2.5;
   Unit<Length> xUnit = Units.METRE;
   Unit<Time> tUnit = MILLI(SECOND);

   // Aus den Rohdaten werden Quantity-Objekte erzeugt.
   Quantity<Time> expectedT = QuantityUtils.createTimeQuantity(tMeasure, tUnit);
   Quantity<Length> expectedX = QuantityUtils.createLengthQuantity(xMeasure, xUnit);

   // Die zu testende Methode wird aufgerufen.
   Event event = EventUtils.createEvent(tMeasure, tUnit, xMeasure, xUnit);

   // Es wird geprüft, ob das Ereignis die richtigen Werte für die Orts- und Zeitkoordinate enthält.
   QuantityUtils.compareQuantities(expectedT, event.t(), 1E-9, 1E-9);
   QuantityUtils.compareQuantities(expectedX, event.x(), 1E-9, 1E-9);
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Testet den Vergleich zweier Ereignisse")
void testCompareEvents()
   {
   // Die Testdaten werden intialisiert.
   Event expectedEvent = EventUtils.createEvent(2.2, SECOND, 0.0, KILO(METRE));
   Event correctEvent = EventUtils.createEvent(2200, MILLI(SECOND), 0.0, METRE);
   Event falseEvent1 = EventUtils.createEvent(2.2001, SECOND, 0.0, KILO(METRE));
   Event falseEvent2 = EventUtils.createEvent(2.2, SECOND, 0.001, KILO(METRE));

   // Die zu testende Methode wird aufgerufen
   assertTrue(EventUtils.compareEvents(expectedEvent, correctEvent,1E-9, 1E-9, 1E-9, 1E-9));
   assertFalse(EventUtils.compareEvents(expectedEvent, falseEvent1,1E-9, 1E-9, 1E-9, 1E-9));
   assertFalse(EventUtils.compareEvents(expectedEvent, falseEvent2,1E-9, 1E-9, 1E-9, 1E-9));
   }
}