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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
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
 * @author Th. K. Walter
 */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "StandardGalileitransformationProvider", port = "8080")
class StandardGalileitransformationPactTest
{

@Pact(provider = "StandardGalileitransformationProvider", consumer = "StandardGalileitransformationConsumer")
public RequestResponsePact transformieren(PactDslWithProvider builder)
   {
   Map<String, String> headers = new HashMap<>();
   headers.put("Content-Type", "application/json");

   String queryString = "t=-5&x=6&y=-7&z=8&v=1";

   return builder.given("normal").uponReceiving("transformiertes Ereignis").path("/transformiere").query(queryString)
      .method("GET").willRespondWith().headers(headers).status(200)
      .body(new PactDslJsonBody().integerType("t", -5).integerType("x", 11).integerType("y", 7).integerType("z", 8))
      .toPact();
   }

@Test
void testArticles(MockServer mockServer) throws IOException
   {
   HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/transformiere?t=-5&x=6&y=-7&z=8&v=1").execute().returnResponse();
   assertEquals(200, httpResponse.getStatusLine().getStatusCode());
   System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), java.nio.charset.StandardCharsets.UTF_8));
   }
}
