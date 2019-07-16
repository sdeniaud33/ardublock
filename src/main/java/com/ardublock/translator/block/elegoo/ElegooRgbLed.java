// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooRgbLed extends AbstractElegoo
{

	public ElegooRgbLed(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super("LED RGB", blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = this.addDigitalWrite(0, 1) + "\n" + 
		this.addDigitalWrite(2, 3) + "\n" + 
		this.addDigitalWrite(4, 5);
		return codePrefix + ret + codeSuffix;
	}

}
