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
 * Diese Klasse repräsentiert ein Ereignis in einer zweidimensionalen Raumzeit.
 * 
 * @author Th. K. Walter
 */
public class Ereignis
{
/* Die x-Koordinate (in m) */
private double x;

/* Die Zeitkoordinate (in s) */
private double t;

// =====================================================================================================================
// =====================================================================================================================

/**
 * Initialisiert das Ereignis durch Angabe von Ort und Zeit. 
 * 
 * @param t die Zeitkoordinate (in s)
 * @param x die x-Koordinate (in m)
 */
public Ereignis(double t, double x)
   {
   this.x = x;
   this.t = t;
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * @return die x-Koordinate (in m)
 */
public double getX()
   {
   return this.x;
   }

/**
 * @return die Zeitkoordinate (in s)
 */
public double getT()
   {
   return this.t;
   }

// =====================================================================================================================
// =====================================================================================================================

@Override
public String toString()
   {
   return "Ereignis [t=" + t + ", x=" + x + "]";
   }
}
