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

import javax.measure.Quantity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.thkwalter.jackson.QuantitySerializer;

/**
 * Diese Klasse startet einen Server, der den {@link StandardGalileitransformation-Services} anbietet.
 * 
 * @author Th. K. Walter
 */
@SpringBootApplication
public class StandardGalileitransformationServer
{
public static void main(String[] args)
   {
   SpringApplication.run(StandardGalileitransformationServer.class, args);
   }


// =====================================================================================================================
// =====================================================================================================================


/**
 * Diese Methode wird vom Jackson-Framework aufgerufen und initialisiert den Jackson-Serializer mit einem 
 * {@link QuantitySerializer} zum Serialisieren von {@link Quantity}-Objekten.
 * 
 * @return ein {@link SimpleModule} das einen {@link QuantitySerializer} kapselt.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Bean
public SimpleModule getJacksonQuantityModule()
   {
   // Ein SimpleModule wird erstellt, das einen QuantitySerializer kapselt.
   SimpleModule module = new SimpleModule("QuantitySerializer", new Version(1, 0, 0, null, null, null));
   module.addSerializer(Quantity.class, new QuantitySerializer());
   
   return module;
   }
}