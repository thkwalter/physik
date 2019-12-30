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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Diese Klasse implementiert den Endpunkt des StandardGalileitransformation-Services.
 * 
 * @author Th. K. Walter
 */
@RestController
public class StandardGalileitransformationController
{
/**
 * Transformiert das mittels der Request-Parameter spezifizierte Ereignis. Die Geschwindigkeit der Standard-
 * Galileitransformation wird mit Hilfe des letzten Request-Parameters bestimmt. 
 * 
 * @param t Die Zeitkoordinate (in s)
 * @param x Die x-Koordinate (in m)
 * @param y Die y-Koordinate (in m)
 * @param z Die z-Koordinate (in m)
 * @param v Die Geschwindigkeit (in m/s)
 * 
 * @return Das transformierte {@link Ereignis}
 */
@RequestMapping("/transformiere")
public Ereignis transformiere(@RequestParam(value = "t") double t, @RequestParam(value = "x") double x,
   @RequestParam(value = "y") double y, @RequestParam(value = "z") double z, @RequestParam(value = "v") double v)
   {
   // Das originale Ereignis wird mit Hilfe der Request-Parameter erzeugt.
   Ereignis originalEreignis = new Ereignis(t, x, y, z);
   
   // Die Galileitransformation wird mit Hilfe des letzten Request-Parameters erzeugt.
   StandardGalileitransformation galileitransformation = new StandardGalileitransformation(v);
   
   // Das originale Ereignis wird transformiert und das transformierte Ereignis zurückgegeben.
   return galileitransformation.transformiere(originalEreignis);
   }
}
