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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests für die Klasse {@link StandardGalileitransformationController}.
 * 
 * @author Th. K. Walter
 */
class StandardGalileitransformationControllerTest
{
/**
 * Test für die Methode
 * {@link StandardGalileitransformationController#transformiere(double, double, double, double, double)}. Der Test prüft
 * nach, ob die Funktionsargumente korrekt an die {@link StandardGalileitransformation} übergeben werden.
 */
@DisplayName("Die Funktionsargumente werden korrekt an die StandardGalileitransformation übergeben.")
@Test
void testTransformiere()
   {
   // Ein Objekt der zu testenden Klasse wird erzeugt.
   StandardGalileitransformationController controller = new StandardGalileitransformationController();
   
   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = controller.transformiere(-5.0, 6.0, -7.0, 8.0, 1.0);
   
   // Anhand des transformierten Ereignisses wird geprüft, ob die Funktionsargumente korrekt verarbeitet werden.
   assertEquals(-5.0, transformiertesEreignis.getT(), 5.0 * 1E-9);
   assertEquals(11.0, transformiertesEreignis.getX(), 11.0 * 1E-9);
   assertEquals(-7.0, transformiertesEreignis.getY(), 7.0 * 1E-9);
   assertEquals(8.0, transformiertesEreignis.getZ(), 8.0 * 1E-9);
   }
}
