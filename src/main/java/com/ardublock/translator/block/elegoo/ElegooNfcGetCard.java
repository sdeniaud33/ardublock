package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooNfcGetCard extends AbstractElegoo {
	
	public ElegooNfcGetCard(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super("NFC get card", blockId, translator, codePrefix, codeSuffix, label);
	}

	//@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
        return codePrefix + "RFID_GetCardId()" + codeSuffix;
	}
	
}