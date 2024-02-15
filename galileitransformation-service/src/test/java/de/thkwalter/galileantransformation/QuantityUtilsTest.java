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

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;

import static javax.measure.MetricPrefix.KILO;
import static javax.measure.MetricPrefix.MILLI;
import static org.junit.jupiter.api.Assertions.*;
import static tech.units.indriya.unit.Units.*;

/**
 * Diese Klasse enthält JUnit-Tests für die Klasse {@link QuantityUtils}.
 */
class QuantityUtilsTest
{
@Test
@DisplayName("Ist-Wert ist null")
void testCompareQuantities1()
   {
   // Die Testdaten werden erstellt.
   Quantity<Length> sollWert = QuantityUtils.createLengthQuantity(-4.0, METRE);
   Quantity<Length> istWert = QuantityUtils.createLengthQuantity(0.0, METRE);

   // Die zu testende Methode wird aufgerufen und der Rückgabewert geprüft.
   assertTrue(QuantityUtils.compareQuantities(sollWert, istWert, 1E-9, 4.001));
   assertFalse(QuantityUtils.compareQuantities(sollWert, istWert, 1E-9, 4.0));
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Soll-Wert ist null")
void testCompareQuantities2()
   {
   // Die Testdaten werden erstellt.
   Quantity<Length> sollWert = QuantityUtils.createLengthQuantity(0.0, METRE);
   Quantity<Length> istWert = QuantityUtils.createLengthQuantity(1.0, METRE);

   // Die zu testende Methode wird aufgerufen und der Rückgabewert geprüft.
   assertTrue(QuantityUtils.compareQuantities(sollWert, istWert, 1E-9, 1.001));
   assertFalse(QuantityUtils.compareQuantities(sollWert, istWert, 1E-9, 1.0));
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Soll- und Ist-Wert sind verschieden null")
void testCompareQuantities3()
   {
   // Die Testdaten werden erstellt.
   Quantity<Length> sollWert = QuantityUtils.createLengthQuantity(1000.0, METRE);
   Quantity<Length> istWert = QuantityUtils.createLengthQuantity(1001.0, METRE);

   // Die zu testende Methode wird aufgerufen und der Rückgabewert geprüft.
   assertTrue(QuantityUtils.compareQuantities(sollWert, istWert, 1.001E-3, 1.0));
   assertFalse(QuantityUtils.compareQuantities(sollWert, istWert, 1E-3, 1.0));
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Soll- und Ist-Wert sind negativ")
void testCompareQuantities4()
   {
   // Die Testdaten werden erstellt.
   Quantity<Length> sollWert = QuantityUtils.createLengthQuantity(1000.0, METRE);
   Quantity<Length> istWert = QuantityUtils.createLengthQuantity(1001.0, METRE);

   // Die zu testende Methode wird aufgerufen und der Rückgabewert geprüft.
   try
      {
      QuantityUtils.compareQuantities(sollWert, istWert, -1.0, 1.0);
      fail("Es hätte eine Ausnahme geworfen werden müssen.");
      }
   catch (IllegalArgumentException ex)
      {
      // Der Test war erfolgreich, weil eine Ausnahme geworfen worden ist.
      }

   try
      {
      QuantityUtils.compareQuantities(sollWert, istWert, 1E-3, -1.0);
      fail("Es hätte eine Ausnahme geworfen werden müssen.");
      }
   catch (IllegalArgumentException ex)
      {
      // Der Test war erfolgreich, weil eine Ausnahme geworfen worden ist.
      }
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Testet die Erzeugung einer Zeitangabe")
void testCreateTimeQuantity()
   {
   // Die zu testende Methode wird aufgerufen.
   Quantity<Time> timeQuantity = QuantityUtils.createTimeQuantity(2000.0, MILLI(SECOND));

   // Es wird geprüft, ob die Zeitangabe korrekt erzeugt worden ist.
   assertEquals(2.0, timeQuantity.toSystemUnit().getValue().doubleValue(),1E-9);
   assertEquals(SECOND, timeQuantity.toSystemUnit().getUnit());
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Testet die Erzeugung einer Ortsangabe")
void testCreateLengthQuantity()
   {
   // Die zu testende Methode wird aufgerufen.
   Quantity<Length> timeQuantity = QuantityUtils.createLengthQuantity(-0.004, KILO(METRE));

   // Es wird geprüft, ob die Ortsangabe korrekt erzeugt worden ist.
   assertEquals(-4.0, timeQuantity.toSystemUnit().getValue().doubleValue(),1E-9);
   assertEquals(METRE, timeQuantity.toSystemUnit().getUnit());
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
@DisplayName("Testet die Erzeugung einer Geschwindigkeitsangabe")
void testCreateSpeedQuantity()
   {
   // Die zu testende Methode wird aufgerufen.
   Quantity<Speed> speedQuantity = QuantityUtils.createSpeedQuantity(3.6, KILOMETRE_PER_HOUR);

   // Es wird geprüft, ob die Geschwindigkeitsangabe korrekt erzeugt worden ist.
   assertEquals(1.0, speedQuantity.toSystemUnit().getValue().doubleValue(),1E-6);
   assertEquals(METRE_PER_SECOND, speedQuantity.toSystemUnit().getUnit());
   }
}