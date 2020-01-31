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
package de.thkwalter.jackson;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.measure.Quantity;
import javax.measure.quantity.Time;
import javax.measure.spi.QuantityFactory;
import javax.measure.spi.ServiceProvider;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonGenerator;

import tech.units.indriya.unit.Units;

/**
 * Testklasse für die Klasse {@link QuantitySerializer}.
 * 
 * @author Th. K. Walter
 */
class QuantitySerializerTest
{
@Test
void test() throws IOException
   {
   // Ein Mock des JsonGenerator wird erstellt.
   JsonGenerator mockedJsonGenerator = mock(JsonGenerator.class);

   // Eine Zeit-Quantity für 12 Minuten wird erstellt.
   ServiceProvider provider = ServiceProvider.current();
   QuantityFactory<Time> timeFactory = provider.getQuantityFactory(Time.class);
   Quantity<Time> tQuantity = timeFactory.create(12, Units.MINUTE);

   // Der Serializer wird aufgerufen
   QuantitySerializer<Time> quantitySerializer = new QuantitySerializer<Time>();
   quantitySerializer.serialize(tQuantity, mockedJsonGenerator, null);
   
   // Es wird geprüft, ob die writeNumber()-Methode mit dem Wert 720 (12 Minuten in Sekunden) aufgerufen worden ist.
   verify(mockedJsonGenerator).writeNumber(720.0);
   }
}
