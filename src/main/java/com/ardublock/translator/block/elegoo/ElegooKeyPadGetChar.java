package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooKeyPadGetChar extends AbstractElegoo {

    public ElegooKeyPadGetChar(Long blockId, Translator translator, String codePrefix, String codeSuffix,
            String label) {
        super("Clavier (getKey)", blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        return codePrefix + "KEYPAD_GetKey()" + codeSuffix;
    }

}
