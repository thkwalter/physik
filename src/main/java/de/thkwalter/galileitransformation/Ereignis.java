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

/**
 * Diese Klasse repräsentiert ein Ereignis in der Raumzeit.
 * 
 * @author Th. K. Walter
 */
public class Ereignis
{
/* Die x-Koordinate (in m) */
private double x;

/* Die y-Koordinate (in m) */
private double y;

/* Die z-Koordinate (in m) */
private double z;

/* Die Zeitkoordinate (in s) */
private double t;

// =============================================================================
// =============================================================================

/**
 * Initialisiert das Ereignis durch Angabe aller Koordinatenwerte. 
 * 
 * @param t die Zeitkoordinate (in s)
 * @param x die x-Koordinate (in m)
 * @param y die y-Koordinate (in m)
 * @param z die z-Koordinate (in m)
 */
public Ereignis(double t, double x, double y, double z)
   {
   this.x = x;
   this.y = y;
   this.z = z;
   this.t = t;
   }

// =============================================================================
// =============================================================================

/**
 * @return die x-Koordinate (in m)
 */
public double x()
   {
   return this.x;
   }

/**
 * @return die y-Koordinate (in m)
 */
public double y()
   {
   return this.y;
   }

/**
 * @return die z-Koordinate (in m)
 */
public double z()
   {
   return this.z;
   }

/**
 * @return die Zeitkoordinate (in s)
 */
public double t()
   {
   return this.t;
   }
}
