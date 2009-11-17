/*
 * utils - I18nAction.java - Copyright © 2009 David Roden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.pterodactylus.util.i18n.gui;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import net.pterodactylus.util.i18n.I18n;
import net.pterodactylus.util.i18n.I18nable;

/**
 * Helper class that initializes actions with values from {@link I18n}.
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public abstract class I18nAction extends AbstractAction implements I18nable {

	/** The I18n basename. */
	private final String i18nName;

	/**
	 * Creates a new action that uses the given name as base name to get values
	 * from {@link I18n}.
	 *
	 * @param i18nName
	 *            The base name of the action
	 */
	public I18nAction(String i18nName) {
		this(i18nName, null);
	}

	/**
	 * Creates a new action that uses the given name as base name to get values
	 * from {@link I18n} and the given icon.
	 *
	 * @param i18nName
	 *            The base name of the action
	 * @param icon
	 *            The icon for the action
	 */
	public I18nAction(String i18nName, Icon icon) {
		this(i18nName, true, icon);
	}

	/**
	 * Creates a new action that uses the given name as base name to get values
	 * from {@link I18n}.
	 *
	 * @param i18nName
	 *            The base name of the action
	 * @param enabled
	 *            Whether the action should be enabled
	 */
	public I18nAction(String i18nName, boolean enabled) {
		this(i18nName, enabled, null);
	}

	/**
	 * Creates a new action that uses the given name as base name to get values
	 * from {@link I18n} and the given icon.
	 *
	 * @param i18nName
	 *            The base name of the action
	 * @param enabled
	 *            Whether the action should be enabled
	 * @param icon
	 *            The icon for the action
	 */
	public I18nAction(String i18nName, boolean enabled, Icon icon) {
		this.i18nName = i18nName;
		if (icon != null) {
			putValue(Action.SMALL_ICON, icon);
		}
		setEnabled(enabled);
		updateI18n();
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateI18n() {
		putValue(Action.NAME, I18n.get(i18nName + ".name"));
		putValue(Action.MNEMONIC_KEY, I18n.getKey(i18nName + ".mnemonic"));
		putValue(Action.ACCELERATOR_KEY, I18n.getKeyStroke(i18nName + ".accelerator"));
		putValue(Action.SHORT_DESCRIPTION, I18n.get(i18nName + ".shortDescription"));
		if (I18n.has(i18nName + ".longDescription")) {
			putValue(Action.LONG_DESCRIPTION, I18n.get(i18nName + ".longDescription"));
		} else {
			putValue(Action.LONG_DESCRIPTION, I18n.get(i18nName + ".shortDescription"));
		}
	}

}