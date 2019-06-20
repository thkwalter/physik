/**
 * Copyright 2019 Th. K. Walter
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests für die Klasse {@link StandardGalileitransformation}.
 * 
 * @author Th. K. Walter
 */
class StandardGalileitransformationTest
{
/** Die Anzahl der testdatensätze, die durch die verschiedenen {@link MethodSource}-Methoden erzeugt werden **/
private final static int ANZAHL_TESTDATENSAETZE = 10;

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}. Der Test prüft nach, dass bei
 * einer Galileitransformation von zwei Systemen in der Standardkonfiguration die Koordinaten t, y und z immer
 * unverändert bleiben.
 */
@DisplayName("t, y, und z bleiben immer unverändert")
@ParameterizedTest
@MethodSource("ereignisseUndGeschwindigkeitenLiefern")
void testTransformiere1(Ereignis originalEreignis, double geschwindigkeit)
   {
   // Eine StandardGalileitransformation wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(geschwindigkeit);

   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = galileitransformation.transformiere(originalEreignis);

   // Die Koordinatenwerte für t, y und z müssen unverändert sein.
   assertEquals(originalEreignis.t(), transformiertesEreignis.t());
   assertEquals(originalEreignis.y(), transformiertesEreignis.y());
   assertEquals(originalEreignis.z(), transformiertesEreignis.z());
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
   // Eine StandardGalileitransformation für die Geschwindigkeit v = 0 wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(0.0);

   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = galileitransformation.transformiere(originalEreignis);

   // Der Koordinatenwert für x muss unverändert sein.
   assertEquals(originalEreignis.x(), transformiertesEreignis.x());
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
void testTransformiere3(Ereignis originalEreignis, double geschwindigkeit)
   {
   // Eine StandardGalileitransformation wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(geschwindigkeit);
   
   // Ein zufälliges Ereignis mit t=0 wird erzeugt.
   double x = originalEreignis.x();
   double y = originalEreignis.y();
   double z = originalEreignis.z();
   Ereignis modifiziertesOriginalEreignis = new Ereignis(0.0, x, y, z);

   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = galileitransformation.transformiere(modifiziertesOriginalEreignis);

   // Der Koordinatenwert für x muss unverändert sein.
   assertEquals(originalEreignis.x(), transformiertesEreignis.x());
   }

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
   // Ein Feld mit zuf"alligen Ereignissen wird geholt.
   Ereignis[] ereignisse = StandardGalileitransformationTest.ereignisseLiefern();

   // Der Zufallszahlengenerator erhält bei jedem Aufruf denselben Samen, damit jedes Mal dieselbe Liste erzeugt wird.
   Random random = new Random(2L);
   List<Double> geschwindigkeiten = random.doubles(StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE, -100, 100)
      .boxed().collect(Collectors.toList());

   // Das Feld mit den Argumenten wird erzeugt. Das erste Argument ist ein Ereignis, das zweite Argument ein Double 
   // Geschwindigkeit.
   Arguments[] argumente = new Arguments[StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE];
   for (int i = 0; i < StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE; i++)
      {
      argumente[i] = Arguments.of(ereignisse[i], geschwindigkeiten.get(i));
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
   List<Double> zufallszahlen = random.doubles(4 * StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE, -100, 100)
      .boxed().collect(Collectors.toList());

   // Die Ereignisse werden erzeugt und einem Feld von Ereignissen hinzugefügt.
   Ereignis[] ereignisse = new Ereignis[StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE];
   for (int i = 0; i < StandardGalileitransformationTest.ANZAHL_TESTDATENSAETZE; i++)
      {
      double t = zufallszahlen.get(4 * i);
      double x = zufallszahlen.get(4 * i + 1);
      double y = zufallszahlen.get(4 * i + 2);
      double z = zufallszahlen.get(4 * i + 3);

      ereignisse[i] = new Ereignis(t, x, y, z);
      }

   return ereignisse;
   }
}
