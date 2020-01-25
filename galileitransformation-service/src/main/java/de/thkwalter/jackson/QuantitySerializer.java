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

import java.io.IOException;

import javax.measure.Quantity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Th. K. Walter
 */
public class QuantitySerializer<Q extends Quantity<Q>> extends JsonSerializer<Quantity<Q>>
{
@Override
public void serialize(Quantity<Q> quantity, JsonGenerator gen, SerializerProvider serializers) throws IOException
   {
   gen.writeNumber(quantity.toSystemUnit().getValue().doubleValue());
   }
}
