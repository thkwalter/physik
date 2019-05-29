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
 * Test für die Methode {@link StandardGalileitransformation#transformiere(Ereignis)}.
 */
@DisplayName("t, y, und z bleiben immer unverändert")
@ParameterizedTest
@MethodSource("ereignisseStreamen")
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

private static Stream<Ereignis> ereignisseStreamen() 
   {
   return Stream.of(new Ereignis(2.5, -5.3, 2.5, 0.9));
   }

}
