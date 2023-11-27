/**
 * Copyright 2019 Th. K. Walter
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
package de.thkwalter.galileitransformation;

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

import static tech.units.indriya.unit.Units.METRE_PER_SECOND;

/**
 * Tests für die Klasse {@link StandardGalileitransformation}.
 *
 * @author Th. K. Walter
 */
class StandardGalileitransformationTest
{
/**
 * Die Anzahl der Testdatensätze, die durch die verschiedenen {@link MethodSource}-Methoden erzeugt werden
 **/
private final static int ANZAHL_TESTDATENSAETZE = 10;

// =====================================================================================================================
// =====================================================================================================================

/**
 * Erzeugt bei jedem Aufruf ein Feld von {@link Arguments}-Objekten bestehend aus zufälligen Ereignissen und
 * Geschwindigkeiten.
 *
 * @return ein Feld von {@link Arguments}-Objekten. Das erste Argument ist jeweils ein Ereignis, das zweite Argument
 * ein {@link Double} (Geschwindigkeit)
 */
private static Arguments[] ereignisseUndGeschwindigkeitenLiefern()
   {
   // Ein Feld mit zufälligen Ereignissen wird geholt.
   Ereignis[] ereignisse = StandardGalileitransformationTest.ereignisseLiefern();

   // Der Zufallszahlengenerator erhält bei jedem Aufruf denselben Samen, damit jedes Mal dieselbe Liste erzeugt wird.
   Random random = new Random(2L);
   List<Double> geschwindigkeiten = random.doubles(StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE, -100,
         100).boxed().toList();

   // Das Feld mit den Argumenten wird erzeugt. Das erste Argument ist ein Ereignis, das zweite Argument ein Double
   // Geschwindigkeit.
   Arguments[] argumente = new Arguments[StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE];
   for (int i = 0; i < StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE; i++)
      {
      Quantity<Speed> v = QuantityHelper.createSpeedQuantity(geschwindigkeiten.get(i), METRE_PER_SECOND);
      argumente[i] = Arguments.of(ereignisse[i], v);
      }

   return argumente;
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Erzeugt bei jedem Aufruf dasselbe Feld von zufälligen Ereignissen.
 *
 * @return ein Feld von {@link Ereignis}-Objekten
 */
private static Ereignis[] ereignisseLiefern()
   {
   // Der Zufallszahlengenerator erhält bei jedem Aufruf denselben Samen, damit
   // jedes Mal dieselbe Liste erzeugt wird.
   Random random = new Random(1L);
   List<Double> zufallszahlen = random.doubles(2 * StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE, -100,
         100).boxed().toList();

   // Die Ereignisse werden erzeugt und einem Feld von Ereignissen hinzugefügt.
   Ereignis[] ereignisse = new Ereignis[StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE];
   for (int i = 0; i < StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE; i++)
      {
      double tMasszahl = zufallszahlen.get(2 * i);
      double xMasszahl = zufallszahlen.get(2 * i + 1);
      ereignisse[i] = EreignisHelper.erzeugeEreignis(tMasszahl, Units.SECOND, xMasszahl, Units.METRE);
      }

   return ereignisse;
   }

// ==================================================================================================================
// ==================================================================================================================

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}. Der Test prüft nach, dass bei
 * einer Galileitransformation von zwei Systemen in der Standardkonfiguration die Koordinate t immer unverändert
 * bleibt.
 */
@DisplayName("t bleibt immer unverändert")
@ParameterizedTest
@MethodSource("ereignisseUndGeschwindigkeitenLiefern")
void testTransformiere1(Ereignis originalEreignis, Quantity<Speed> geschwindigkeit)
   {
   // Eine Standard-Galileitransformation wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(geschwindigkeit);

   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = galileitransformation.transformiere(originalEreignis);

   // Die Zeitkoordinate muss unverändert sein.
   QuantityHelper.compareQuantities(originalEreignis.t(), transformiertesEreignis.t(), 1E+9, 1E-9);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}. Der Test prüft nach, dass bei
 * einer Galileitransformation von zwei Systemen in der Standardkonfiguration die Koordinate x unverändert bleibt,
 * falls v = 0 gilt.
 */
@DisplayName("x bleibt unverändert, falls v = 0")
@ParameterizedTest
@MethodSource("ereignisseLiefern")
void testTransformiere2(Ereignis originalEreignis)
   {
   // Eine Standard-Galileitransformation für die Geschwindigkeit v = 0 wird erzeugt.
   Quantity<Speed> v = QuantityHelper.createSpeedQuantity(0.0, METRE_PER_SECOND);
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(v);

   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = galileitransformation.transformiere(originalEreignis);

   // Der Koordinatenwert für x muss unverändert sein.
   QuantityHelper.compareQuantities(originalEreignis.x(), transformiertesEreignis.x(), 1E-9, 1E-9);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}. Der Test prüft nach, dass bei
 * einer Galileitransformation von zwei Systemen in der Standardkonfiguration die Koordinate x unverändert bleibt,
 * falls t = 0 gilt.
 */
@DisplayName("x bleibt unverändert, falls t = 0")
@ParameterizedTest
@MethodSource("ereignisseUndGeschwindigkeitenLiefern")
void testTransformiere3(Ereignis originalEreignis, Quantity<Speed> geschwindigkeit)
   {
   // Eine StandardGalileitransformation wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(geschwindigkeit);

   // Ein zufälliges Ereignis mit t=0 wird erzeugt.
   Quantity<Time> tQuantity = QuantityHelper.createTimeQuantity(0.0, Units.SECOND);
   Ereignis modifiziertesOriginalEreignis = new Ereignis(tQuantity, originalEreignis.x());

   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = galileitransformation.transformiere(modifiziertesOriginalEreignis);

   // Der Koordinatenwert für x muss unverändert sein.
   QuantityHelper.compareQuantities(originalEreignis.x(), transformiertesEreignis.x(), 1E-9, 1E-9);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}. Der Test prüft nach, dass nach
 * einer Galileitransformation und der anschließenden Rücktransformation alle Koordinaten unverändert bleiben.
 */
@DisplayName("Alle Koordinaten bleiben nach Transformation und Rücktransformation unverändert")
@ParameterizedTest
@MethodSource("ereignisseUndGeschwindigkeitenLiefern")
void testTransformiere4(Ereignis originalEreignis, Quantity<Speed> geschwindigkeit)
   {
   // Eine StandardGalileitransformation wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(geschwindigkeit);

   // Die Rücktransformation wird erzeugt.
   StandardGalileitransformation ruecktransformation =
         new StandardGalileitransformation(geschwindigkeit.multiply(-1.0));

   // Die Galileitransformation wird ausgeführt.
   Ereignis temporaeresEreignis = galileitransformation.transformiere(originalEreignis);

   // Die Rücktransformation wird ausgeführt.
   Ereignis transformiertesEreignis = ruecktransformation.transformiere(temporaeresEreignis);

   // Die Ereignisse werden verglichen
   EreignisHelper.compareEreignisse(originalEreignis, transformiertesEreignis, 1E-9, 1E-9, 1E-9, 1E-9);
   }

// ==================================================================================================================
// ==================================================================================================================

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}. Der Test prüft für vier Fälle
 * nach, dass sich die x-Koordinate korrekt transformiert, falls v und t ungleich 0 sind.
 */
@DisplayName("Die x-Koordinate transformiert korrekt, falls v und t ungleich null sind.")
@Test
void testTransformiere5()
   {
   // Eine StandardGalileitransformation wird erzeugt.
   Quantity<Speed> v = QuantityHelper.createSpeedQuantity(1.0, METRE_PER_SECOND);
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(v);

   // Vier originale Ereignisse werden erzeugt.
   Quantity<Length> xSollQuantity1 = QuantityHelper.createLengthQuantity(1.0, Units.METRE);
   Ereignis originalEreignis1 = EreignisHelper.erzeugeEreignis(1.0, Units.SECOND, 1.0, Units.METRE);

   Quantity<Length> xSollQuantity2 = QuantityHelper.createLengthQuantity(5.0, Units.METRE);
   Ereignis originalEreignis2 = EreignisHelper.erzeugeEreignis(-2.0, Units.SECOND, 3.0, Units.METRE);

   Quantity<Length> xSollQuantity3 = QuantityHelper.createLengthQuantity(-19.0, Units.METRE);
   Ereignis originalEreignis3 = EreignisHelper.erzeugeEreignis(9.0, Units.SECOND, -10.0, Units.METRE);

   Quantity<Length> xSollQuantity4 = QuantityHelper.createLengthQuantity(-1.0, Units.METRE);
   Ereignis originalEreignis4 = EreignisHelper.erzeugeEreignis(-13.0, Units.SECOND, -14.0, Units.METRE);

   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis1 = galileitransformation.transformiere(originalEreignis1);
   Ereignis transformiertesEreignis2 = galileitransformation.transformiere(originalEreignis2);
   Ereignis transformiertesEreignis3 = galileitransformation.transformiere(originalEreignis3);
   Ereignis transformiertesEreignis4 = galileitransformation.transformiere(originalEreignis4);

   // Die x-Koordinate wurde korrekt transformiert.
   QuantityHelper.compareQuantities(transformiertesEreignis1.x(), xSollQuantity1, 1E-9, 1E-9);
   QuantityHelper.compareQuantities(transformiertesEreignis2.x(), xSollQuantity2, 1E-9, 1E-9);
   QuantityHelper.compareQuantities(transformiertesEreignis3.x(), xSollQuantity3, 1E-9, 1E-9);
   QuantityHelper.compareQuantities(transformiertesEreignis4.x(), xSollQuantity4, 1E-9, 1E-9);
   }
}