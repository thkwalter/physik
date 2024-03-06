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
package de.thkwalter.galileantransformation;

import com.fasterxml.jackson.databind.module.SimpleModule;
import de.thkwalter.jackson.QuantitySerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.measure.Quantity;

/**
 * Diese Klasse startet einen Server, der den {@link GalileanBoost -Services} anbietet.
 * 
 * @author Th. K. Walter
 */
@SpringBootApplication
public class GalileanTransformationServer
{
public static void main(String[] args)
   {
   SpringApplication.run(GalileanTransformationServer.class, args);
   }


// =====================================================================================================================
// =====================================================================================================================


/**
 * Diese Methode wird vom Jackson-Framework aufgerufen und initialisiert den Jackson-Serializer mit einem 
 * {@link QuantitySerializer} zum Serialisieren von {@link Quantity}-Objekten.
 * 
 * @return ein {@link SimpleModule} das einen {@link QuantitySerializer} kapselt.
 */
@SuppressWarnings("rawtypes")
@Bean
public SimpleModule getJacksonQuantityModule()
   {
   // Ein SimpleModule wird erstellt, das einen QuantitySerializer kapselt.
   SimpleModule module = new SimpleModule();
   //noinspection unchecked
   module.addSerializer(Quantity.class, new QuantitySerializer());

   return module;
   }
}