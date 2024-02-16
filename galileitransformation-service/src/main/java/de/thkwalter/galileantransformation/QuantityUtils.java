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
package de.thkwalter.galileantransformation;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;
import javax.measure.spi.QuantityFactory;
import javax.measure.spi.ServiceProvider;

/**
 * Diese Klasse enthält Methoden, um den Umgang mit {@link Quantity}--Objekten zu erleichtern.
 */
public class QuantityUtils
{

/**
 * Mithilfe dieser {@link QuantityFactory} lassen sich {@link Quantity}--Objekte für Zeitangaben erstellen.
 */
private static QuantityFactory<Time> timeFactory;

/**
 * Mithilfe dieser {@link QuantityFactory} lassen sich {@link Quantity}--Objekte für Längenangaben erstellen.
 */
private static QuantityFactory<Length> lengthFactory;

/**
 * Mithilfe dieser {@link QuantityFactory} lassen sich {@link Quantity}--Objekte für Geschwindigkeitsangaben erstellen.
 */
private static QuantityFactory<Speed> speedFactory;

// =====================================================================================================================
// =====================================================================================================================

static
   {
   // Die QuantityFactory-Objekte werden erzeugt.
   ServiceProvider provider = ServiceProvider.current();

   QuantityUtils.timeFactory = provider.getQuantityFactory(Time.class);
   QuantityUtils.lengthFactory = provider.getQuantityFactory(Length.class);
   QuantityUtils.speedFactory = provider.getQuantityFactory(Speed.class);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Diese Methode vergleicht zwei {@link Quantity}-Objekte und gibt <tt>true</tt> zurück, falls sie (innerhalb
 * gegebener Toleranzbereiche) miteinander übereinstimmen. Sind beide Werte verschieden von null, so wird
 * geprüft, ob die relative Abweichung zwischen den Werten kleiner als der Wert des Arguments
 * <tt>relativeTolerance</tt> ist. Ist einer der beiden Werte gleich null, so wird geprüft, ob der andere Wert
 * kleiner als der Wert des Arguments <tt>absoluteTolerance</tt> ist.
 *
 * @param expectedValue           der erwartete Wert
 * @param actualValue            der vorhandene Wert
 * @param relativeTolerance die zulässige, relative Abweichung
 * @param absoluteTolerance die zulässige absolute Abweichung (falls einer der Werte gleich null ist)
 * @return <tt>true</tt>, falls beide Werte im Rahmen der gegebenen Toleranzbereiche übereinstimmen; <tt>false</tt>
 * sonst
 */
public static <Q extends Quantity<Q>> boolean compareQuantities(Quantity<Q> expectedValue, Quantity<Q> actualValue,
      double relativeTolerance, double absoluteTolerance)
   {
   // Ist die relative Abweichung nicht größer als null, so wird eine Ausnahme geworfen.
   if (relativeTolerance <= 0)
      {
      throw new IllegalArgumentException("Das Argument 'relativeTolerance' muss größer als Null sein!");
      }

   // Ist die absolute Abweichung nicht größer als null, so wird eine Ausnahme geworfen.
   if (absoluteTolerance <= 0)
      {
      throw new IllegalArgumentException("Das Argument 'absoluteTolerance' muss größer als Null sein!");
      }

   // Die Größen werden in die SI-Einheit umgerechnet und dann wird jeweils ihre Maßzahl bestimmt.
   double expectedMeasure = expectedValue.toSystemUnit().getValue().doubleValue();
   double actualMeasure = actualValue.toSystemUnit().getValue().doubleValue();

   double difference = expectedMeasure - actualMeasure;

   // Falls beide Werte ungleich null sind, ...
   if (expectedMeasure != 0 && actualMeasure != 0)
      {
      return (Math.abs(difference / expectedMeasure) < relativeTolerance);
      }

   // Falls einer der beiden Werte gleich null ist, ...
   else
      {
      return Math.abs(difference) < absoluteTolerance;
      }
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Diese Methode erstellt aus der Maßzahl und der Einheit der Zeitkoordinate ein {@link Quantity}-Objekt.
 *
 * @param measure die Maßzahl der Zeitkoordinate
 * @param unit    die Einheit der Zeitkoordinate
 * @return das erstellte {@link Quantity}-Objekt
 */
public static Quantity<Time> createTimeQuantity(double measure, Unit<Time> unit)
   {
   return QuantityUtils.timeFactory.create(measure, unit);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Diese Methode erstellt aus der Maßzahl und der Einheit der Ortskoordinate ein {@link Quantity}-Objekt.
 *
 * @param measure die Maßzahl der Ortskoordinate
 * @param unit    die Einheit der Ortskoordinate
 * @return das erstellte {@link Quantity}-Objekt
 */
public static Quantity<Length> createLengthQuantity(double measure, Unit<Length> unit)
   {
   return QuantityUtils.lengthFactory.create(measure, unit);
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Diese Methode erstellt aus der Maßzahl und der Einheit der Geschwindigkeit ein {@link Quantity}-Objekt.
 *
 * @param measure die Maßzahl der Geschwindigkeit
 * @param unit    die Einheit der Geschwindigkeit
 * @return das erstellte {@link Quantity}-Objekt
 */
public static Quantity<Speed> createSpeedQuantity(double measure, Unit<Speed> unit)
   {
   return QuantityUtils.speedFactory.create(measure, unit);
   }
}
