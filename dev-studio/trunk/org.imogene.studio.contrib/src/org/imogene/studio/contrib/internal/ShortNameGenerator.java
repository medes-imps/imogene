package org.imogene.studio.contrib.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;


/**
 * Generates the short names that are not field.
 */
public class ShortNameGenerator {
	private EList<CardEntity> cardEntities;

	// Used shortnames
	private List<String> shortNames;

	private CardEntity _currentCard;

	private FieldGroup _currentGroup;

	private FieldEntity _currentField;

	private static int ascii_A = (int) 'A';

	// list of shortNames which will be given to card entities
	private List<String> _cardShortNamesToAttribute;
	private List<String> _groupShortNamesToAttribute;
	private List<String> _fieldShortNamesToAttribute;

	// index used to give the next entity shortName
	private int _cardIndex;
	private int _groupIndex;
	private int _fieldIndex;

	public ShortNameGenerator(EList<CardEntity> entities) {
		cardEntities = entities;
		shortNames = new ArrayList<String>();
	}

	/**
	 * Looks through cards', groups' and fields' shortnames, removes duplicates
	 * shortNames and creates the list of the shortnames.
	 */
	public void listShortNames(boolean removeDuplicates) {
		// --- get all allready defined shortnames
		for (CardEntity cardEntity : cardEntities) {
			// remove shortname if already used for another entity
			// and stores it if not ""
			handleShortName(cardEntity, removeDuplicates);
			for (FieldGroup fieldGroup : cardEntity.getGroups()) {
				handleShortName(fieldGroup, removeDuplicates);
				for (FieldEntity fieldEntity : fieldGroup.getFields()) {
					handleShortName(fieldEntity, removeDuplicates);
				}

			}
		}
	}

	/**
	 * @return the list of the shortnames used in the model
	 */
	public List<String> getShortNames() {
		return shortNames;
	}

	/**
	 * Sets the next card of the list as the current card and returns its
	 * generated shortName.
	 * 
	 * @return the shortName (generated if not defined yet)
	 */
	public String getNextCardShortName() {
		if (_cardShortNamesToAttribute == null) {
			_cardShortNamesToAttribute = generateShortNamesList("", //$NON-NLS-1$
					countMissingShortNames(cardEntities));
			_cardIndex = 0;
		}

		// Sets the generator as working on a new entity
		_currentCard = cardEntities.get(_cardIndex);
		_currentGroup = null;
		_groupShortNamesToAttribute = null;
		_currentField = null;
		_fieldShortNamesToAttribute = null;
		if (_currentCard == null)
			return null;

		// Test if a shortname was already defined for this entity
		String res = _currentCard.getShortName();
		if (res == null || "".equals(res)) //$NON-NLS-1$
			res = _cardShortNamesToAttribute.remove(0);

		_cardIndex++;
		return res;
	}

	/**
	 * Sets the next group of the list as the current group and returns its
	 * generated shortName.
	 * 
	 * @return the shortName (generated if not defined yet)
	 */
	public String getNextGroupShortName() {
		List<FieldGroup> fieldGroups = _currentCard.getGroups();
		if (_groupShortNamesToAttribute == null) {
			_groupShortNamesToAttribute = generateShortNamesList(_currentCard
					.getShortName(), countMissingShortNames(fieldGroups));
			_groupIndex = 0;
		}

		// Sets the generator as working on a new entity
		_currentGroup = fieldGroups.get(_groupIndex);
		_currentField = null;
		_fieldShortNamesToAttribute = null;
		if (_currentGroup == null)
			return null;

		// Test if a shortname was already defined for this entity
		String res = _currentGroup.getShortName();
		if (res == null || "".equals(res)) //$NON-NLS-1$
			res = _groupShortNamesToAttribute.remove(0);

		_groupIndex++;
		return res;
	}

	/**
	 * Sets the next field of the list as the current field and returns its
	 * generated shortName.
	 * 
	 * @return the shortName (generated if not defined yet)
	 */
	public String getNextFieldShortName() {
		List<FieldEntity> fieldEntities = _currentGroup.getFields();
		if (_fieldShortNamesToAttribute == null) {
			_fieldShortNamesToAttribute = generateShortNamesList(_currentGroup
					.getShortName(), countMissingShortNames(fieldEntities));
			_fieldIndex = 0;
		}

		// Sets the generator as working on a new entity
		_currentField = fieldEntities.get(_fieldIndex);
		if (_currentField == null)
			return null;

		// Test if a shortname was already defined for this entity
		String res = _currentField.getShortName();
		if (res == null || "".equals(res)) //$NON-NLS-1$
			res = _fieldShortNamesToAttribute.remove(0);

		_fieldIndex++;
		return res;
	}

	// -------------- PRIVATE ------------
	private void handleShortName(CardEntity cardEntity, boolean removeDuplicates) {
		String shortName = cardEntity.getShortName();
		if (removeDuplicates && shortNames.contains(shortName)) {
			cardEntity.setShortName(""); //$NON-NLS-1$
		}
		// store entity's shortname
		if (shortName != null && !"".equals(shortName)) { //$NON-NLS-1$
			shortNames.add(shortName);
		}
	}

	private void handleShortName(FieldGroup fieldGroup, boolean removeDuplicates) {
		String shortName = fieldGroup.getShortName();
		if (removeDuplicates && shortNames.contains(shortName)) {
			fieldGroup.setShortName(""); //$NON-NLS-1$
		}
		// store entity's shortname
		if (shortName != null && !"".equals(shortName)) { //$NON-NLS-1$
			shortNames.add(shortName);
		}
	}

	private void handleShortName(FieldEntity fieldEntity,
			boolean removeDuplicates) {
		String shortName = fieldEntity.getShortName();
		if (removeDuplicates && shortNames.contains(shortName)) {
			fieldEntity.setShortName(""); //$NON-NLS-1$
		}
		// store entity's shortname
		if (shortName != null && !"".equals(shortName)) { //$NON-NLS-1$
			shortNames.add(shortName);
		}
	}

	private List<String> generateShortNamesList(String prefix,
			int nbShortNames, boolean force2chars) {
		List<String> res = new ArrayList<String>();

		// use a prefix if too many entities
		Character local_prefix = (nbShortNames > 25 || force2chars) ? 'A'
				: null;

		String used_prefix = (local_prefix == null) ? "" : local_prefix //$NON-NLS-1$
				.toString();
		if (prefix != null)
			used_prefix = prefix + used_prefix;

		int j = 0;
		for (int i = 0; i < nbShortNames; i++) {
			String nextSN = used_prefix + new Character((char) (ascii_A + j));
			while (shortNames.contains(nextSN)) {
				j++;
				if (j == 26) {
					j = 0;
					if (local_prefix == null) {
						// we will finally need longest shortnames so... let's
						// start again...
						return generateShortNamesList(prefix, nbShortNames,
								true);
					}
					// compute the new prefix
					local_prefix = new Character((char) (local_prefix++));
					used_prefix = prefix + local_prefix;
				}
				nextSN = used_prefix + new Character((char) (ascii_A + j));
			}
			res.add(nextSN);
			j++;
			if (j == 26) {
				j = 0;
				if (local_prefix == null) {
					// we will finally need longest shortnames so... let's start
					// again...
					return generateShortNamesList(prefix, nbShortNames, true);
				}
				// compute the new prefix
				local_prefix = new Character((char) (local_prefix + 1));
				used_prefix = prefix + local_prefix;
			}
		}

		shortNames.addAll(res);
		return res;
	}

	private List<String> generateShortNamesList(String prefix, int nbShortNames) {
		return generateShortNamesList(prefix, nbShortNames, false);
	}

	@SuppressWarnings("unchecked")
	private int countMissingShortNames(List cardEntities2) {
		int res = 0;
		if (!cardEntities2.isEmpty()) {
			if (cardEntities2.get(0) instanceof CardEntity) {
				for (CardEntity cardEntity : (List<CardEntity>) cardEntities2) {
					String sn = cardEntity.getShortName();
					if (sn == null || "".equals(sn)) //$NON-NLS-1$
						res++;
				}
			} else if (cardEntities2.get(0) instanceof FieldGroup) {
				for (FieldGroup cardEntity : (List<FieldGroup>) cardEntities2) {
					String sn = cardEntity.getShortName();
					if (sn == null || "".equals(sn)) //$NON-NLS-1$
						res++;
				}
			} else if (cardEntities2.get(0) instanceof FieldEntity) {
				for (FieldEntity cardEntity : (List<FieldEntity>) cardEntities2) {
					String sn = cardEntity.getShortName();
					if (sn == null || "".equals(sn)) //$NON-NLS-1$
						res++;
				}
			}
		}
		return res;
	}

}
