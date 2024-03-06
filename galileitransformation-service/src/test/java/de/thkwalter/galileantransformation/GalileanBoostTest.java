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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.units.indriya.unit.Units.METRE_PER_SECOND;

/**
 * Diese Klasse enthält JUnit-Tests für die Klasse {@link GalileanBoost}.
 *
 * @author Th. K. Walter
 */
class GalileanBoostTest
{
/**
 * Die Anzahl der Testdatensätze, die durch die verschiedenen {@link MethodSource}-Methoden erzeugt werden
 **/
private final static int NUMBER_TEST_RECORDS = 10;

// =====================================================================================================================
// =====================================================================================================================

/**
 * Erzeugt bei jedem Aufruf ein Feld von {@link Arguments}-Objekten bestehend aus zufälligen Ereignissen und
 * Geschwindigkeiten.
 *
 * @return ein Feld von {@link Arguments}-Objekten. Das erste Argument ist jeweils ein {@link Event}, das zweite
 * Argument ein {@link Quantity}-Objekt, das eine Geschwindigkeit repräsentiert
 */
private static Arguments[] createEventsAndVelocities()
   {
   // Ein Feld mit zufälligen Ereignissen wird erzeugt.
   Event[] events = GalileanBoostTest.createEvents();

   // Der Zufallszahlengenerator erhält bei jedem Aufruf denselben Samen, damit jedes Mal dieselbe Liste erzeugt wird.
   Random random = new Random(2L);
   List<Double> randomNumbers = random.doubles(GalileanBoostTest.NUMBER_TEST_RECORDS, -100,
         100).boxed().toList();

   // Das Feld mit den Argumenten wird erzeugt. Das erste Argument ist ein Ereignis, das zweite Argument eine
   // Geschwindigkeit.
   Arguments[] arguments = new Arguments[GalileanBoostTest.NUMBER_TEST_RECORDS];
   for (int i = 0; i < GalileanBoostTest.NUMBER_TEST_RECORDS; i++)
      {
      Quantity<Speed> v = QuantityUtils.createSpeedQuantity(randomNumbers.get(i), METRE_PER_SECOND);
      arguments[i] = Arguments.of(events[i], v);
      }

   return arguments;
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Erzeugt bei jedem Aufruf dasselbe Feld von zufälligen Ereignissen.
 *
 * @return ein Feld von {@link Event}-Objekten
 */
private static Event[] createEvents()
   {
   // Der Zufallszahlengenerator erhält bei jedem Aufruf denselben Samen, damit
   // jedes Mal dieselbe Liste erzeugt wird.
   Random random = new Random(1L);
   List<Double> randomNumbers = random.doubles(2 * GalileanBoostTest.NUMBER_TEST_RECORDS, -100,
         100).boxed().toList();

   // Die Ereignisse werden erzeugt und einem Feld von Ereignissen hinzugefügt.
   Event[] events = new Event[GalileanBoostTest.NUMBER_TEST_RECORDS];
   for (int i = 0; i < GalileanBoostTest.NUMBER_TEST_RECORDS; i++)
      {
      double tMeasure = randomNumbers.get(2 * i);
      double xMeasure = randomNumbers.get(2 * i + 1);
      events[i] = EventUtils.createEvent(tMeasure, Units.SECOND, xMeasure, Units.METRE);
      }

   return events;
   }

// ==================================================================================================================
// ==================================================================================================================

/**
 * Test für die Methode {@link GalileanBoost#transform(Event)}. Der Test prüft nach, ob bei einer
 * Galilei-Boost-Transformation die Koordinate t immer unverändert bleibt.
 */
@DisplayName("Testet, ob die Transformation, die Zeitkoordinate unverändert lässt")
@ParameterizedTest
@MethodSource("createEventsAndVelocities")
void testTransform1(Event originalEvent, Quantity<Speed> v)
   {
   // Eine Galilei-Boost-Transformation wird erzeugt.
   GalileanBoost galileanBoost = new GalileanBoost(v);

   // Die zu testende Methode wird aufgerufen.
   Event transformedEvent = galileanBoost.transform(originalEvent);

   // Die Zeitkoordinate muss unverändert sein.
   assertTrue(QuantityUtils.compareQuantities(originalEvent.t(), transformedEvent.t(), 1E+9, 1E-9));
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode {@link GalileanBoost#transform(Event)}. Der Test prüft nach, ob bei einer
 * Galilei-Boost-Transformation die Koordinate x unverändert bleibt, falls v = 0 gilt.
 */
@DisplayName("Testet, ob bei der Transformation x unverändert bleibt, falls v = 0 ist")
@ParameterizedTest
@MethodSource("createEvents")
void testTransform2(Event originalEvent)
   {
   // Eine Galilei-Boost-Transformation für die Geschwindigkeit v = 0 wird erzeugt.
   Quantity<Speed> v = QuantityUtils.createSpeedQuantity(0.0, METRE_PER_SECOND);
   GalileanBoost galileanBoost = new GalileanBoost(v);

   // Die zu testende Methode wird aufgerufen.
   Event transformedEvent = galileanBoost.transform(originalEvent);

   // Die Ortskoordinate muss unverändert sein.
   assertTrue(QuantityUtils.compareQuantities(originalEvent.x(), transformedEvent.x(), 1E-9, 1E-9));
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode {@link GalileanBoost#transform(Event)}. Der Test prüft nach, ob bei einer
 * * Galilei-Boost-Transformation die Koordinate x unverändert bleibt, falls t = 0 gilt.
 */
@DisplayName("Testet, ob bei der Transformation x unverändert bleibt, falls t = 0 ist")
@ParameterizedTest
@MethodSource("createEventsAndVelocities")
void testTransform3(Event originalEvent, Quantity<Speed> v)
   {
   // Eine Galilei-Boost-Transformation wird erzeugt.
   GalileanBoost galileanBoost = new GalileanBoost(v);

   // Ein zufälliges Ereignis mit t=0 wird erzeugt.
   Quantity<Time> tQuantity = QuantityUtils.createTimeQuantity(0.0, Units.SECOND);
   Event modifiedEvent = new Event(tQuantity, originalEvent.x());

   // Die zu testende Methode wird aufgerufen.
   Event transformedEvent = galileanBoost.transform(modifiedEvent);

   // Der Koordinatenwert für x muss unverändert sein.
   assertTrue(QuantityUtils.compareQuantities(originalEvent.x(), transformedEvent.x(), 1E-9, 1E-9));
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode {@link GalileanBoost#transform(Event)}. Der Test prüft nach, ob bei einer
 * Galilei-Boost-Transformation und anschließender Rücktransformation alle Koordinaten unverändert bleiben.
 */
@DisplayName("Testet, ob nach einer Transformation und Rücktransformation alle Koordinaten unverändert sind")
@ParameterizedTest
@MethodSource("createEventsAndVelocities")
void testTransform4(Event originalEvent, Quantity<Speed> v)
   {
   // Eine Galilei-Boost-Transformation wird erzeugt.
   GalileanBoost galileanBoost = new GalileanBoost(v);

   // Die Rücktransformation wird erzeugt.
   GalileanBoost inverseTransformation = new GalileanBoost(v.multiply(-1.0));

   // Die Galileitransformation wird ausgeführt.
   Event temporaryEvent = galileanBoost.transform(originalEvent);

   // Die Rücktransformation wird ausgeführt.
   Event transformedEvent = inverseTransformation.transform(temporaryEvent);

   // Die Ereignisse werden verglichen
   assertTrue(EventUtils.compareEvents(originalEvent, transformedEvent, 1E-9, 1E-9, 1E-9, 1E-9));
   }

// ==================================================================================================================
// ==================================================================================================================

/**
 * Test für die Methode {@link GalileanBoost#transform(Event)}. Der Test prüft für vier Fälle nach, dass sich die
 * x-Koordinate korrekt transformiert, falls v und t ungleich 0 sind.
 */
@DisplayName("Testet, ob die x-Koordinate korrekt transformiert, falls v und t ungleich null sind.")
@Test
void testTransform5()
   {
   // Eine StandardGalileitransformation wird erzeugt.
   Quantity<Speed> v = QuantityUtils.createSpeedQuantity(1.0, METRE_PER_SECOND);
   GalileanBoost galileanBoost = new GalileanBoost(v);

   // Vier originale Ereignisse und die erwarteten Ortskoordinaten werden erzeugt.
   Quantity<Length> expectedX1 = QuantityUtils.createLengthQuantity(0.0, Units.METRE);
   Event originalEvent1 = EventUtils.createEvent(1.0, Units.SECOND, 1.0, Units.METRE);

   Quantity<Length> expectedX2 = QuantityUtils.createLengthQuantity(5.0, Units.METRE);
   Event originalEvent2 = EventUtils.createEvent(-2.0, Units.SECOND, 3.0, Units.METRE);

   Quantity<Length> expectedX3 = QuantityUtils.createLengthQuantity(-19.0, Units.METRE);
   Event originalEvent3 = EventUtils.createEvent(9.0, Units.SECOND, -10.0, Units.METRE);

   Quantity<Length> expectedX4 = QuantityUtils.createLengthQuantity(-1.0, Units.METRE);
   Event originalEvent4 = EventUtils.createEvent(-13.0, Units.SECOND, -14.0, Units.METRE);

   // Die zu testende Methode wird aufgerufen.
   Event transformedEvent1 = galileanBoost.transform(originalEvent1);
   Event transformedEvent2 = galileanBoost.transform(originalEvent2);
   Event transformedEvent3 = galileanBoost.transform(originalEvent3);
   Event transformedEvent4 = galileanBoost.transform(originalEvent4);

   // Die x-Koordinate wurde korrekt transformiert.
   assertTrue(QuantityUtils.compareQuantities(transformedEvent1.x(), expectedX1, 1E-9, 1E-9));
   assertTrue(QuantityUtils.compareQuantities(transformedEvent2.x(), expectedX2, 1E-9, 1E-9));
   assertTrue(QuantityUtils.compareQuantities(transformedEvent3.x(), expectedX3, 1E-9, 1E-9));
   assertTrue(QuantityUtils.compareQuantities(transformedEvent4.x(), expectedX4, 1E-9, 1E-9));
   }
}