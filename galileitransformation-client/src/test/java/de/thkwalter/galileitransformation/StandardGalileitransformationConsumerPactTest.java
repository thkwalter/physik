/**
 * Copyright 2019 Th. K. Walter
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.thkwalter.galileitransformation;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Ein Test zur Erzeugung und Verifizierung der Pact-Datei für den Standard-Galileitransformationsservice.
 *
 * @author Th. K. Walter
 */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "StandardGalileitransformationProvider")
class StandardGalileitransformationConsumerPactTest
{

/**
 * Erzeugt das {@link RequestResponsePact}--Objekt, welches durch Serilisierung die Pact-Datei ergibt.
 *
 * @param builder der {@link PactDslWithProvider}, mit dessen Hilfe das {@link RequestResponsePact}--Objekt erzeugt
 *                wird.
 * @return das {@link RequestResponsePact}--Objekt, welches durch Serilisierung die Pact-Datei ergibt
 */
@Pact(provider = "StandardGalileitransformationProvider", consumer = "StandardGalileitransformationConsumer")
public V4Pact transformiere(PactBuilder builder)
   {
   // Der Content-Type-Header wird auf "application/json" gesetzt.
   Map<String, String> headers = new HashMap<>();
   headers.put("Content-Type", "application/json");

   String queryString = "v=1&t=-2&x=3";

   return builder.usingLegacyDsl().given("standard").uponReceiving("transformiertes Ereignis").path(
         "/transformiere").query(queryString).method("GET").willRespondWith().headers(headers).status(200).body(
         new PactDslJsonBody().numberType("t", -2).numberType("x", 5)).toPact(V4Pact.class);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Dieser Test verifiziert, dass die erzeugt Pact-Datei, die Interaktion mit dem Server korrekt beschreibt.
 *
 * @param mockServer der mit Hilfe der Pact-Datei erzeugte Server-Mock
 * @throws IOException
 */
@Test
void testTransformiere(MockServer mockServer) throws IOException
   {
   WebTestClient client = WebTestClient.bindToServer().baseUrl(mockServer.getUrl()).build();
   client.get().uri("/transformiere?t=-2&x=3&v=1").exchange().expectStatus().isOk();

//
//   // Das JSON im Body wird geparsed.
//   BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),
//         StandardCharsets.UTF_8));
//   String json = reader.readLine();
//   JSONTokener tokener = new JSONTokener(json);
//   JSONObject jsonWurzel = new JSONObject(tokener);
//
//   // Es wird überprüft, ob der JSON-Body die erwarteten Werte enthält.
//   assertEquals(jsonWurzel.getDouble("x"), 5);
//   assertEquals(jsonWurzel.getDouble("t"), -2);
//
//   // Es wird überprüft, dass der JSON-Body ausschließlich die erwarteten Werte enthält.
//   assertEquals(jsonWurzel.keySet().size(), 2);
   }
}
