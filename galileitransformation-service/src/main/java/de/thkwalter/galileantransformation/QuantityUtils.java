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
 * gegebeber Toleranzbereiche) miteinander übereinstimmen. Sind beide Werte verschieden von null, so wird
 * geprüft, ob die relative Abweichung zwischen den Werten kleiner als der Wert des Arguments
 * <tt>relativeAbweichung</tt> ist. Ist einer der beiden Werte gleich null, so wird geprüft, ob der andere Wert
 * kleiner als der Wert des Arguments <tt>absoluteAbweichung</tt> ist.
 *
 * @param sollWert           der erwartete Wert
 * @param istWert            der vorhandene Wert
 * @param relativeAbweichung die zulässige, relative Abweichung
 * @param absoluteAbweichung die zulässige absolute Abweichung (falls einer der Werte gleich null ist)
 * @param <Q>                Der Name einer von {@link Quantity} abgeleiteten Klasse.
 * @return <tt>true</tt>, falls beide Werte im Rahmen der gegebenen Toleranzbereiche übereinstimmen; <tt>false</tt>
 * sonst
 */
public static <Q extends Quantity<Q>> boolean compareQuantities(Quantity<Q> sollWert, Quantity<Q> istWert,
      double relativeAbweichung, double absoluteAbweichung)
   {
   // Ist die relative Abweichung nicht größer als null, so wird eine Ausnahme geworfen.
   if (relativeAbweichung <= 0)
      {
      throw new IllegalArgumentException("Das Argument 'relativeAbweichung' muss größer als Null sein!");
      }

   // Ist die absolute Abweichung nicht größer als null, so wird eine Ausnahme geworfen.
   if (absoluteAbweichung <= 0)
      {
      throw new IllegalArgumentException("Das Argument 'absoluteAbweichung' muss größer als Null sein!");
      }

   // Die Größen werden in die SI-Einheit umgerechnet und dann wird jeweils ihre Maßzahl bestimmt.
   double sollMasszahl = sollWert.toSystemUnit().getValue().doubleValue();
   double istMasszahl = istWert.toSystemUnit().getValue().doubleValue();

   double differenz = sollMasszahl - istMasszahl;

   // Falls beide Werte ungleich null sind, ...
   if (sollMasszahl != 0 && istMasszahl != 0)
      {
      return (Math.abs(differenz / sollMasszahl) < relativeAbweichung);
      }

   // Falls einer der beiden Werte gleich null ist, ...
   else
      {
      return Math.abs(differenz) < absoluteAbweichung;
      }
   }

// =====================================================================================================================
// =====================================================================================================================

/**
 * Diese Methode erstellt aus der Maßzahl und der Einheit einer Zeitkoordinate ein {@link Quantity}-Objekt.
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
 * Diese Methode erstellt aus der Maßzahl und der Einheit einer Ortskoordinate ein {@link Quantity}-Objekt.
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
 * Diese Methode erstellt aus der Maßzahl und der Einheit einer Ortskoordinate ein {@link Quantity}-Objekt.
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
