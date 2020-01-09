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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;

/**
 * @author Th. K. Walter
 */
@Provider("StandardGalileitransformationProvider")
@PactFolder("pacts")
@Tag("pact-provider-test")
public class StandardGalileitransformationProviderPactTest
{
private static ConfigurableWebApplicationContext application;

@BeforeAll
public static void startService()
   {
   StandardGalileitransformationProviderPactTest.application = (ConfigurableWebApplicationContext) SpringApplication
      .run(StandardGalileitransformationServer.class);
   }

@BeforeEach
void before(PactVerificationContext context)
   {
   context.setTarget(new HttpTestTarget("localhost", 8080, "/"));
   }

@TestTemplate
@ExtendWith(PactVerificationInvocationContextProvider.class)
void testTemplate(PactVerificationContext context) 
   {
   context.verifyInteraction();
   }

@AfterAll
public static void stopService()
   {
   StandardGalileitransformationProviderPactTest.application.close(); 
   }
}
