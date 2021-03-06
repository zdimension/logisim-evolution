/*
 * This file is part of logisim-evolution.
 *
 * Logisim-evolution is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Logisim-evolution is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with logisim-evolution. If not, see <http://www.gnu.org/licenses/>.
 *
 * Original code by Carl Burch (http://www.cburch.com), 2011.
 * Subsequent modifications by:
 *   + College of the Holy Cross
 *     http://www.holycross.edu
 *   + Haute École Spécialisée Bernoise/Berner Fachhochschule
 *     http://www.bfh.ch
 *   + Haute École du paysage, d'ingénierie et d'architecture de Genève
 *     http://hepia.hesge.ch/
 *   + Haute École d'Ingénierie et de Gestion du Canton de Vaud
 *     http://www.heig-vd.ch/
 */

package com.cburch.logisim.prefs;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.Preferences;

class PrefMonitorInt extends AbstractPrefMonitor<Integer> {
  private final int dflt;
  private int value;

  PrefMonitorInt(String name, int dflt) {
    super(name);
    this.dflt = dflt;
    this.value = dflt;
    Preferences prefs = AppPreferences.getPrefs();
    set(prefs.getInt(name, dflt));
    prefs.addPreferenceChangeListener(this);
  }

  public Integer get() {
    return value;
  }

  public void preferenceChange(PreferenceChangeEvent event) {
    Preferences prefs = event.getNode();
    String prop = event.getKey();
    String name = getIdentifier();
    if (prop.equals(name)) {
      int oldValue = value;
      int newValue = prefs.getInt(name, dflt);
      if (newValue != oldValue) {
        value = newValue;
        AppPreferences.firePropertyChange(
            name, oldValue, newValue);
      }
    }
  }

  public void set(Integer newValue) {
    int newVal = newValue;
    if (value != newVal) {
      AppPreferences.getPrefs().putInt(getIdentifier(), newVal);
    }
  }
}
