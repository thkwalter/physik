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

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;

/**
 * Der Pact-Provider-Test für den {@link StandardGalileitransformationService}.
 * 
 * @author Th. K. Walter
 */
@Provider("StandardGalileitransformationProvider")
@PactFolder("pacts")
@Disabled
public class StandardGalileitransformationProviderPactTest
{
/** Der {@link ConfigurableWebApplicationContext} wird benötigt, um den Service nach den Tests wieder zu stoppen. */
private static ConfigurableWebApplicationContext application;

/**
 * Startet den Service bevor die Tests ausgeführt werden.
 */
@BeforeAll
public static void serviceStarten()
   {
   StandardGalileitransformationProviderPactTest.application = (ConfigurableWebApplicationContext) SpringApplication
      .run(GalileanTransformationServer.class);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Vor jedem einzelnen Test wird die URL festgelegt, unter welcher der Service zu erreichen ist.
 * 
 * @param context Der {@link TestTemplateInvocationContext}, der an jeden Aufruf des Test-Templates übergeben wird.
 */
@BeforeEach
void urlSetzen(PactVerificationContext context)
   {
   context.setTarget(new HttpTestTarget("localhost", 8080, "/"));
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Ruft das Test-Template für jeden in der Pact-Datei spezifizierten Test einmal auf.
 * 
 * @param context Der {@link TestTemplateInvocationContext}, der an jeden Aufruf des Test-Templates übergeben wird.
 */
@TestTemplate
@ExtendWith(PactVerificationInvocationContextProvider.class)
void testAusfuehren(PactVerificationContext context) 
   {
   context.verifyInteraction();
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Stoppt den Service nachdem alle Tests ausgeführt worden sind.
 */
@AfterAll
public static void serviceStoppen()
   {
   StandardGalileitransformationProviderPactTest.application.close(); 
   }
}
