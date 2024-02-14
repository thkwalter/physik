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

import com.ibm.icu.impl.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.measure.Unit;
import javax.measure.format.MeasurementParseException;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;

import static tech.units.indriya.unit.Units.METRE;
import static tech.units.indriya.unit.Units.SECOND;

/**
 * Tests für die Klasse {@link StandardGalileitransformationService}.
 *
 * @author Th. K. Walter
 */
class StandardGalileitransformationServiceTest
{
/**
 * Test für die Methode
 * {@link StandardGalileitransformationService#transformiere(double, String, double, String, double, String)}. Der Test prüft
 * nach, ob die Funktionsargumente korrekt an die {@link StandardGalileitransformation} übergeben werden.
 */
@DisplayName("Die Funktionsargumente werden korrekt an die StandardGalileitransformation übergeben.")
@Test
void testTransformiere1()
   {
   // Die Testdaten werden intialisiert.
   double tMasszahl = -2.0;
   Unit<Time> tEinheit = SECOND;
   double xMasszahl = 3.0;
   Unit<Length> xEinheit = METRE;
   double vMasszahl = 1.0;
   String vSymbol = "m/s";
   Event sollEvent = EreignisHelper.erzeugeEreignis(tMasszahl, tEinheit, xMasszahl, xEinheit);

   // Ein Objekt der zu testenden Klasse wird erzeugt.
   StandardGalileitransformationService controller = new StandardGalileitransformationService();

   // Die zu testende Methode wird aufgerufen.
   Event transformiertesEvent = controller.transformiere(tMasszahl, tEinheit.getSymbol(), xMasszahl,
         xEinheit.getSymbol(), vMasszahl, vSymbol);

   double tTransMasszahl = transformiertesEvent.t().getValue().doubleValue();
   String tTransSymbol = transformiertesEvent.t().getUnit().getSymbol();
   double xTransMasszahl = transformiertesEvent.x().getValue().doubleValue();
   String xTransSymbol = transformiertesEvent.x().getUnit().getSymbol();

   // Die Rücktransformation wird ausgeführt
   Event istEvent = controller.transformiere(tTransMasszahl, tTransSymbol, xTransMasszahl, xTransSymbol,
         -vMasszahl, vSymbol);

   // Soll- und Ist-Ereignis werden verglichen.
   EreignisHelper.compareEreignisse(sollEvent, istEvent, 1E-9, 1E-9, 1E-9, 1E-9);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Test für die Methode
 * {@link StandardGalileitransformationService#transformiere(double, String, double, String, double, String)}. Der Test prüft
 * nach, ob die Funktionsargumente korrekt an die {@link StandardGalileitransformation} übergeben werden.
 */
@DisplayName("Der Test prüft das Verhalten, wenn das Symbol für eine Einheit nicht erkannt wird.")
@Test
void testTransformiere2()
   {
   // Ein Objekt der zu testenden Klasse wird erzeugt.
   StandardGalileitransformationService controller = new StandardGalileitransformationService();

   try
      {
      controller.transformiere(1.0, "t", 2.0, "s", 3.0, "m:s");
      Assert.fail("Eine MeasurementParseException hätte geworfen werden müssen");
      }
   catch (MeasurementParseException e)
      {
      // Der Test war erfolgreich.
      }
   }
}
