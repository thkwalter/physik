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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests für die Klasse {@link StandardGalileitransformation}.
 * 
 * @author Th. K. Walter
 */
class StandardGalileitransformationTest
{

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}. Der Test prüft nach, dass bei
 * einer Galileitransformation von zwei Systemen in der Standardkonfiguration die Koordinaten t, y und z immer
 * unverändert bleiben.
 */
@DisplayName("t, y, und z bleiben immer unverändert")
@ParameterizedTest
@MethodSource("ereignislisteErzeugen")
void testTransformiere1(Ereignis originalEreignis)
   {
   // Eine beliebige StandardGalileitransformation wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(-4.0);

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
 * Erzeugt bei jedem Aufruf dieselbe Liste von zehn zufälligen Ereignissen.
 * 
 * @return eine {@link List} mit {@link Ereignis}-Objekten
 */
private static List<Ereignis> ereignislisteErzeugen()
   {
   // Die Anzahl der erzeugte Ereignisse
   int anzahlEreignisse = 10;

   // Der Zufallszahlengenerator erhält bei jedem Aufruf denselben Samen, damit
   // jedes Mal dieselbe Liste erzeugt wird.
   Random random = new Random(1L);
   List<Double> zufallszahlen = random.doubles(4 * anzahlEreignisse, -100, 100).boxed().collect(Collectors.toList());

   // Die Ereignisse werden erzeugt und der Ereignisliste hinzugefügt.
   List<Ereignis> ereignisse = new ArrayList<Ereignis>();
   for (int i = 0; i < anzahlEreignisse; i++)
      {
      double t = zufallszahlen.get(4 * i);
      double x = zufallszahlen.get(4 * i + 1);
      double y = zufallszahlen.get(4 * i + 2);
      double z = zufallszahlen.get(4 * i + 3);

      ereignisse.add(new Ereignis(t, x, y, z));
      }

   return ereignisse;
   }
}
