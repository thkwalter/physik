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
 * Tests für die Klasse {@link StandardGalileitransformation}.
 * 
 * @author Th. K. Walter
 */
class StandardGalileitransformationTest
{

/**
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}.
 */
@DisplayName("t, y, und z bleiben immer unverändert")
@Test
void testTransformiere1()
   {
   // Eine beliebige StandardGalileitransformation wird erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(-4.0);
   
   // Ein Ereignis wird erzeugt.
   double t = -2.0;
   double x = -1.0;
   double y = 4.0;
   double z = 0.0;
   Ereignis originalEreignis = new Ereignis(t, x, y, z);
   
   // Die zu testende Methode wird aufgerufen.
   Ereignis transformiertesEreignis = galileitransformation.transformiere(originalEreignis);
   
   // Die Koordinatenwerte für t, y und z müssen unverändert sein.
   assertEquals(t, transformiertesEreignis.t());
   assertEquals(y, transformiertesEreignis.y());
   assertEquals(z, transformiertesEreignis.z());
   }

}
