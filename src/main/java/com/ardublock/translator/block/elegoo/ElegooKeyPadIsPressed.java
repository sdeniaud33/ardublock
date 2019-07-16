package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooKeyPadIsPressed extends AbstractElegoo {

    public ElegooKeyPadIsPressed(Long blockId, Translator translator, String codePrefix, String codeSuffix,
            String label) {
        super("Clavier (key pressed)", blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        return codePrefix + "KEYPAD_isKeyPressed()" + codeSuffix;
    }

}
