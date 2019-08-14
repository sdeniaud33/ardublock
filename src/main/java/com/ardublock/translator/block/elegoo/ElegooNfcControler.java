package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ElegooNfcControler extends AbstractElegoo {

	public ElegooNfcControler(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super("Controleur NFC", blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException, BlockException {
		ArgValueResult pin = getArgValue(0);
		if (pin.argType != ArgType.number)
			throw new BlockException(blockId, controlDisplayName + " - le 1er numéro de broche doit être un nombre");
		int rstPin = Integer.parseInt(pin.value);
		pin = getArgValue(1);
		if (pin.argType != ArgType.number)
			throw new BlockException(blockId, controlDisplayName + " - le 2eme numéro de broche doit être un nombre");
		int sdaPin = Integer.parseInt(pin.value);

		translator.addHeaderFile("SPI.h");
		translator.addHeaderFile("MFRC522.h");

		translator.addSetupCommand("SPI.begin();");
		translator.addSetupCommand("mfrc522.PCD_Init();");

		String definitions = "MFRC522 mfrc522(" + sdaPin + ", " + rstPin + ");\n"
			+ "String RFID_GetCardId() {\n"
			+ "\tif (!mfrc522.PICC_IsNewCardPresent()) return \"\";\n"
			+ "\tif (!mfrc522.PICC_ReadCardSerial()) return \"\";\n"
			+ "\tString cardId = \"\";\n"
			+ "\tfor(int i=0;i<=mfrc522.uid.size;i++) {\n"
            + "\t\tint b = mfrc522.uid.uidByte[i];\n"
            + "\t\tif (b < 0x10) cardId += \"0\";\n"
            + "\t\tcardId += (String(b, HEX));\n"
			+ "\t}" 
			+ "\tmfrc522.PICC_HaltA();\n"	
			+ "\treturn cardId;\n"	
			+ "}\n";

		translator.addDefinitionCommand(definitions);
		return "";
	}
}
