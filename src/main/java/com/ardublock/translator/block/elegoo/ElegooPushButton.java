// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooPushButton extends AbstractElegoo {

	public ElegooPushButton(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super("Bouton", blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String ret = addDigitalRead(0, true);
		return codePrefix + ret + codeSuffix;
	}

}
