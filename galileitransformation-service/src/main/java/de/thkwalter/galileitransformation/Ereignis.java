/**
 * Copyright 2023 Th. K. Walter
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

import javax.measure.Quantity;
import javax.measure.quantity.Time;

/**
 * Diese Klasse repr&auml;sentiert ein Ereignis in einer zweidimensionalen Raumzeit.
 *
 * @author Thomas K. Walter
 *
 * @param t Die Zeitkoordinate
 * @param x Die x-koordinate (in m)
 */
public record Ereignis (Quantity<Time> t, double x) {}
