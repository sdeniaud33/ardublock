package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class StringAdd extends TranslatorBlock
{
	public StringAdd(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "";
		TranslatorBlock translatorBlock = this.getTranslatorBlockAtSocket(0, codePrefix, codeSuffix);
		ret += translatorBlock.toCode();
		ret += " + ";
		translatorBlock = this.getTranslatorBlockAtSocket(1, codePrefix, codeSuffix);
		ret += translatorBlock.toCode();
		return ret;
	}

}
