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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.json.JSONTokener;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

/**
 * Ein Test zur Erzeugung und Verifizierung der Pact-Datei für den Standard-Galileitransformationsservice.
 * 
 * @author Th. K. Walter
 */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "StandardGalileitransformationProvider", port = "8080")
class StandardGalileitransformationConsumerPactTest
{

/**
 * Erzeugt das {@link RequestResponsePact}--Objekt, welches durch Serilisierung die Pact-Datei ergibt.
 * 
 * @param builder der {@link PaPactDslWithProvider}, mit dessen Hilfe das {@RequestResponsePact}--Objekt erzeugt wird.
 * 
 * @return das {@link RequestResponsePact}--Objekt, welches durch Serilisierung die Pact-Datei ergibt
 */
@Pact(provider = "StandardGalileitransformationProvider", consumer = "StandardGalileitransformationConsumer")
public RequestResponsePact transformiere(PactDslWithProvider builder)
   {
   // Der Content-Type-Header wird auf "application/json" gesetzt.
   Map<String, String> headers = new HashMap<>();
   headers.put("Content-Type", "application/json");

   String queryString = "v=1&t=-2&x=3";

   return builder./*given("standard").*/uponReceiving("transformiertes Ereignis").path("/transformiere").query(queryString)
      .method("GET").willRespondWith().headers(headers).status(200)
      .body(new PactDslJsonBody().numberType("t", -2).numberType("x", 5)).toPact();
   }

// =====================================================================================================================
// =====================================================================================================================

@Test
/**
 * Dieser Test verifiziert, dass die erzeugt Pact-Datei, die Interaktion mit dem Server korrekt beschreibt.
 *
 * @param mockServer der mit Hilfe der Pact-Datei erzeugte Server-Mock 
 * 
 * @throws IOException
 */
void testTransformiere(MockServer mockServer) throws IOException
   {
   // Der Mock-Server wird aufgerufen.
   HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/transformiere?t=-2&x=3&v=1").execute()
      .returnResponse();
   
   // Es wird geprüft, ob der Status-Code 200 ist.
   assertEquals(200, httpResponse.getStatusLine().getStatusCode());
   
   // Das JSON im Body wird geparsed.
   BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
   String json = reader.readLine();
   JSONTokener tokener = new JSONTokener(json);
   JSONObject jsonWurzel = new JSONObject(tokener);
   
   // Es wird überprüft, ob der JSON-Body die erwarteten Werte enthält.
   assertEquals(jsonWurzel.getInt("x"), 5);
   assertEquals(jsonWurzel.getInt("t"), -2);
   
   // Es wird überprüft, dass der JSON-Body ausschließlich die erwarteten Werte enthält.
   assertEquals(jsonWurzel.keySet().size(), 2);
   }
}
